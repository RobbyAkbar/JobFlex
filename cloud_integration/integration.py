#!/usr/bin/python3
from google.cloud import bigquery
from google.cloud import storage
import flask
from flask import request, jsonify, abort
import json


#for ML
import tensorflow as tf
import numpy as np
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
import PyPDF2
import pandas as pd
import os
from sklearn.preprocessing import LabelBinarizer

# Load labels
filename = 'train_labels.csv'
data = pd.read_csv(filename, header=0, names=['Query'])

filename2 = 'train_descs.csv'
data2 = pd.read_csv(filename2, header = 0, names = ['Description'])

# Initialize tokenizer
tokenizer = Tokenizer(num_words = 3000)
tokenizer.fit_on_texts(data2['Description'])

#Load Model
model = tf.keras.models.load_model('../saved_model')
predicted = model.predict(token_list, verbose = 0)

app = flask.Flask(__name__)
app.config["DEBUG"] = True

bucketName="job-flex-storage"

@app.route('/', methods=['GET'])
def home():
    return '''<h1>This server doesn't handle GET</h1>
<p>Use Post Instead</p>'''

def jobQuery(jobNameList):
    client = bigquery.Client()
    toQuery = "SELECT * from `b21-cap0139-jobflex.jobsData.main_jobs_data` where lower(Title) LIKE '%"+jobNameList[0].lower()+"%'"
    for jobName in jobNameList[1:]:
        toQuery += " OR lower(Title) LIKE '%"+jobName.lower()+"%'"
    toQuery+= " LIMIT 10"
    query_job = client.query(
        toQuery
    )

    results = query_job.result()  # Waits for job to complete.
    toReturn = [dict(row) for row in results]
    for x in toReturn:
        x["EndDate"]=x["EndDate"].strftime("%Y-%m-%d %H:%M:%S.%f")
    return (toReturn)
  
def resultQuery(id):
    client = bigquery.Client()
    query_job = client.query(
        '''
        SELECT
            JSON_EXTRACT_SCALAR(h,'$.JobID') as JobID,
            JSON_EXTRACT_SCALAR(h,'$.WindowID') as WindowID,
            JSON_EXTRACT_SCALAR(h,'$.Title') as Title,
            JSON_EXTRACT_SCALAR(h,'$.Description') as Description,
            JSON_EXTRACT_SCALAR(h,'$.Requirements') as Requirements,
            JSON_EXTRACT_SCALAR(h,'$.City') as City,
            JSON_EXTRACT_SCALAR(h,'$.State') as State,
            JSON_EXTRACT_SCALAR(h,'$.Country') as Country,
            JSON_EXTRACT_SCALAR(h,'$.Zip5') as Zip5,
            JSON_EXTRACT_SCALAR(h,'$.StartDate') as StartDate,
            JSON_EXTRACT_SCALAR(h,'$.EndDate') as EndDate
        FROM `b21-cap0139-jobflex.jobsData.results_data`
        LEFT join unnest(json_extract_array(recommendation)) as h
        '''+'WHERE id LIKE "'+id+'"'
    )
    results = query_job.result()  # Waits for job to complete.
    toReturn = [dict(row) for row in results]
    return (toReturn)
  
def download_blob(bucket_name, source_blob_name, destination_file_name):
    storage_client = storage.Client()
    bucket = storage_client.bucket(bucket_name)

    # Construct a client side representation of a blob.
    # Note `Bucket.blob` differs from `Bucket.get_blob` as it doesn't retrieve
    # any content from Google Cloud Storage. As we don't need additional data,
    # using `Bucket.blob` is preferred here.
    blob = bucket.blob(source_blob_name)
    blob.download_to_filename(destination_file_name)

    print(
        "Blob {} downloaded to {}.".format(
            source_blob_name, destination_file_name
        )
    )
    
def getPrediction(filename):
    # Read and extract text from PDF file
    pdf_file = filename
    try:
        pdf_read = PyPDF2.PdfFileReader(pdf_file)
        page = pdf_read.getPage(0)
        page_content = page.extractText()
    except:
        page_content = ""

    token_list = tokenizer.texts_to_sequences([page_content])[0]
    token_list = pad_sequences([token_list], maxlen = 1200, padding = 'post')

    encoder = LabelBinarizer()
    encoder.fit(data['Query'])

    prediction = encoder.classes_[np.argmax(predicted)]
    return str(prediction)
  
def appendRecommend(id,recommendation):
    client = bigquery.Client()
    rowsToInsert = [
        {u"id":id,u"recommendation":recommendation}
    ]
    errors = client.insert_rows_json(
        "jobsData.results_data", rowsToInsert, row_ids=[None] * len(rowsToInsert)
    )  # Make an API request.
    if errors == []:
        print("New rows have been added.")
    else:
        print("Encountered errors while inserting rows: {}".format(errors))

@app.route('/search', methods=['POST'])
def search():
    request_data = request.get_json()
    if "toSearch" in request_data:
        queryResult = jobQuery(request_data["toSearch"].split())
        return jsonify(queryResult)
    else:
        abort(404,description = 'Wrong post method, make sure to use JSON with "toSearch" as the key')
        
@app.route('/pdfPredict', methods=['POST'])
def pdfPredict():
    request_data = request.get_json()
    filename = request_data["name"]
    contentType = request_data["contentType"]
    if (contentType == "multipart/form-data" and ".pdf" in filename)or(contentType=="application/pdf"):
        download_blob(bucketName,filename,filename[4:])
        prediction=getPrediction(filename[4:])
        print(prediction)
        recommendation=jobQuery(prediction.split())
        appendRecommend(filename[4:],json.dumps(recommendation))
        os.remove(filename[4:])
        return(f"It's a PDF, and the prediction is {prediction}")
    else:
        print("It's not a PDF file so I don't care")
        return("It's not a PDF")

@app.route('/getRecommendation', methods=['POST'])
def getRecommendation():
    request_data = request.get_json()
    if "id" in request_data:
        queryResult = resultQuery(request_data["id"]+".pdf")
        return jsonify(queryResult)
    else:
        abort(404,description = 'Wrong post method, make sure to use JSON with "id" as the key')

app.run(host = "0.0.0.0",port=8080)
