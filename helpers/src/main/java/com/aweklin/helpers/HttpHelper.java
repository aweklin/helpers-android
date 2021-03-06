/**
 * This is a HTTP RESTFUL request wrapper. It allows you to make RESTFUL api calls asynchronously.
 * It Initiates a new HTTP (REST) request with the given URL [and parameters in case of POST]
 * You can use it for free in your projects without restrictions.
 *
 * @author:  Akeem Aweda <akeem@aweklin.com>
 * @created: 2018-06-04
 * @description:
 */

package com.aweklin.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class HttpHelper {

    public enum RequestType {
        get,
        post
    }

    public AsyncTask<String, Void, Response> request(Context context, RequestType requestType, String url, List<NameValuePair> headers, String parameters, Boolean showLoader, String loadingText) {
        return new RequestTask(context, requestType, url, headers, parameters, showLoader, loadingText).execute("");
    }


    public class RequestTask extends AsyncTask<String, Void, Response> {

        private Boolean showLoader;
        private String loadingText;
        private RequestType requestType;
        private String url;
        private String parameters;
        private ProgressDialog progressDialog;
        private List<NameValuePair> headers;

        public RequestTask(Context context, RequestType requestType, String url, List<NameValuePair> headers, String parameters, Boolean showLoader, String loadingText) {
            this.requestType = requestType;
            this.url = url;
            this.parameters = parameters;
            this.showLoader = showLoader;
            this.loadingText = loadingText;
            this.headers = headers;
            if (showLoader) {
                this.progressDialog = new ProgressDialog(context);
            }
        }

        @Override
        protected Response doInBackground(String... url) {
            Response serverResponse = new Response();

            try {
                HttpResponse httpResponse;

                int timeoutSocket = 20000;
                int timeoutConnection = 20000;

                HttpParams httpParams = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParams, timeoutSocket);
                HttpConnectionParams.setSoTimeout(httpParams, timeoutConnection);

                HttpClient httpClient = new DefaultHttpClient(httpParams);

                if (requestType == RequestType.get) {
                    HttpGet httpGet = new HttpGet(this.url);
                    httpGet.addHeader("Content-Type", "application/json");
                    httpGet.addHeader("Accept", "application/json");

                    if (headers != null) {
                        for (int i = 0; i < headers.size(); i++) {
                            httpGet.addHeader(headers.get(i).getName(), headers.get(i).getValue());
                        }
                    }

                    httpResponse = httpClient.execute(httpGet);
                } else {
                    HttpPost httpPost = new HttpPost(this.url);
                    httpPost.addHeader("Content-Type", "application/json");
                    httpPost.addHeader("Accept", "application/json");

                    if (headers != null) {
                        for (int i = 0; i < headers.size(); i++) {
                            httpPost.addHeader(headers.get(i).getName(), headers.get(i).getValue());
                        }
                    }

                    httpPost.setEntity(new StringEntity(parameters, "UTF-8"));
                    httpResponse = httpClient.execute(httpPost);
                }

                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    serverResponse.data = EntityUtils.toString(httpResponse.getEntity());
                    serverResponse.isSuccessful = true;
                    Log.i("", serverResponse.data.toString());
                } else {
                    serverResponse.message = "Failed to get server response";
                }
            } catch (IOException e) {
                serverResponse.message = e.getMessage();
            } catch (Exception e) {
                serverResponse.message = e.getMessage();
            }

            return serverResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (this.showLoader) {
                this.progressDialog.setMessage(this.loadingText);
                this.progressDialog.show();
            }
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);

            if (this.showLoader) {
                if (this.progressDialog.isShowing()) {
                    this.progressDialog.dismiss();
                }
            }
        }
    }

    public class Response {

        public boolean isSuccessful = false;
        public String message = "";
        public Object data = null;

    }

}
