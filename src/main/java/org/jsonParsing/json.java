package org.jsonParsing;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class json {
    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\sample.json";
        System.out.println("path is: " + path);
        String requestBody = readRequestBody(path);
        String valueToBeAddedUpdated = "Ranikhet";
        String nodeWhereValueNeedToUpdate = "$.address.city";
        requestBody = setJsonDataValue(nodeWhereValueNeedToUpdate, valueToBeAddedUpdated, requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("After setting value: " + requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");

        requestBody = addJsonNode("$","lastName", "Mathpal",requestBody);
        System.out.println("After adding node in root level:" + requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");

        requestBody = addJsonNode("$.address","landmark", "More Market",requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("After adding node to a node inside root:" + requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");

        String nodeName = "vehicle";
        Map<String, Object> finalMap = prepareJsonMapForVehicle(nodeName);
        requestBody = addJsonMap(requestBody, finalMap);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("After adding a jsonMap inside a json:" + requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");

        String nodeName1 = "phoneDetails";
        JsonArray phoneDetails = new JsonArray();
        requestBody =addJsonNodeAsArray("$", nodeName1,phoneDetails,requestBody);
        requestBody = addJsonArrayValues("$.phoneDetails", requestBody,  "iphone", "123") ;
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("After adding a jsonArray inside a json:" +requestBody);
        System.out.println("-------------------------------------------------------------------------------------------");
        requestBody = addJsonArrayValues("$.phoneDetails", requestBody,  "android", "456") ;
        System.out.println("After adding another value to the jsonArray inside a json:" +requestBody);
    }

    private static String addJsonArrayValues(String jsonPath, String requestBody, String arrayNodeName, String arrayNodeValue) {
        DocumentContext documentContext= JsonPath.parse(requestBody);
        documentContext.add(JsonPath.compile(jsonPath),prepareJsonMapForPhone(arrayNodeName, arrayNodeValue));
        String latestIne = documentContext.jsonString();
        return latestIne;
    }

    private static Map<String, Object> prepareJsonMapForPhone(String type, String number) {
        Map<String, Object> phoneMap = new LinkedHashMap<>();
        phoneMap.put("type", type);
        phoneMap.put("number", number);
        return phoneMap;
    }

    private static Map<String, Object> prepareJsonMapForVehicle(String nodeName) {
        Map<String, Object> finalMap = new LinkedHashMap<>();
        Map<String, Object> vehicleMap = new LinkedHashMap<>();
        vehicleMap.put("name", "honda");
        vehicleMap.put("model", "GS");
        vehicleMap.put("price", "15.8 L");
        finalMap.put(nodeName, vehicleMap);
        return finalMap;
    }

    private static String addJsonMap(String requestBody, Map<String, Object> finalMap) {
        HashMap<String, Object> map = new Gson().fromJson(requestBody, HashMap.class);
        map.putAll(finalMap);
        return new Gson().toJson(map);
    }

    private static String addJsonNode(String jsonpath, String nodeName, String nodeValue, String requestBody) {
        DocumentContext documentContext = JsonPath.parse(requestBody);
        documentContext.put(JsonPath.compile(jsonpath), nodeName,  nodeValue);
        String updatedRequestBody = documentContext.jsonString();
        return updatedRequestBody;
    }

    private static String addJsonNodeAsArray(String jsonpath, String nodeName, JsonArray nodeValue, String requestBody) {
        DocumentContext documentContext = JsonPath.parse(requestBody);
        documentContext.put(JsonPath.compile(jsonpath), nodeName,  nodeValue);
        String updatedRequestBody = documentContext.jsonString();
        return updatedRequestBody;
    }

    private static String setJsonDataValue(String nodeWhereValueNeedToUpdate, Object valueToBeAddedUpdated, String requestBody) {
        return JsonPath.parse(requestBody).set(nodeWhereValueNeedToUpdate, valueToBeAddedUpdated).jsonString();
    }

    private static String readRequestBody(String requestBodyPath) throws IOException {
        File requestBody = new File(requestBodyPath);
        return JsonPath.parse(requestBody).jsonString();
    }
}
