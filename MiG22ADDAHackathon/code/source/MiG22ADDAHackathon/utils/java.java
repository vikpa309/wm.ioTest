package MiG22ADDAHackathon.utils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import org.apache.commons.io.IOUtils;
import com.softwareag.util.IDataMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.*;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.*;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataUtil;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
// --- <<IS-END-IMPORTS>> ---

public final class java

{
	// ---( internal utility methods )---

	final static java _instance = new java();

	static java _newInstance() { return new java(); }

	static java _cast(Object o) { return (java)o; }

	// ---( server methods )---




	public static final void aggregateTestData (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(aggregateTestData)>> ---
		// @sigtype java 3.5
		// [i] record:1:required input
		// [i] - field:0:required name
		// [i] - field:0:required url
		// [i] field:0:required filter
		// [i] field:0:required extension
		// [i] field:0:required apiType
		// [i] object:0:required isRESTInvokeEnabled
		// [o] record:1:required testData
		// [o] - field:0:required ReqFileName
		// [o] - field:0:required ReqFileURL
		// [o] - field:0:required ResponseFileName
		// [o] - field:0:required ResponseFileURL
		// [o] - field:0:required ReqNativeFileName
		// [o] - field:0:required ReqNativeFileURL
		// [o] - field:0:required RespNativeFileName
		// [o] - field:0:required RespNativeFileURL
		// [o] - field:0:required ReqHeaderFileName
		// [o] - field:0:required ReqHeaderFileURL
		// [o] - field:0:required RespHeaderFileName
		// [o] - field:0:required RespHeaderFileURL
		// [o] - field:0:required ReqNativeHeaderFileName
		// [o] - field:0:required ReqNativeHeaderFileURL
		// [o] - field:0:required RespNativeHeaderFileName
		// [o] - field:0:required RespNativeHeaderFileURL
		// [o] - field:0:required RequestNumber
		// [o] - field:0:required OperationName
		// [o] - field:0:required HTTPMethod
		List<String> rls=null;
		List<IData> reqTestList=new ArrayList<IData>();
		HashMap<String, String> map = new HashMap<>();
		
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			
			String	filter = IDataUtil.getString( pipelineCursor, "filter" );
			String	extension = IDataUtil.getString( pipelineCursor, "extension" );
			String	apiType = IDataUtil.getString( pipelineCursor, "apiType" );
			Object	isRESTInvokeEnabled = IDataUtil.get( pipelineCursor, "isRESTInvokeEnabled" );
			IData[]	input = IDataUtil.getIDataArray( pipelineCursor, "input" );
			pipelineCursor.destroy();
			//String requestRegex = "^"+filter+".+"+"Request"+"\\."+extension+"$";	
			String requestRegex = "^"+filter+".+"+"_Request\\."+".+$";
			//Pattern reqFileNamePattern = Pattern.compile(requestRegex);
			if ( input != null){
				for ( int i = 0; i < input.length; i++ ){
					
					IDataCursor inputCursor = input[i].getCursor();
					String	name = IDataUtil.getString( inputCursor, "name" );
					String	url = IDataUtil.getString( inputCursor, "url" );
					map.put(name,url);
				inputCursor.destroy();
				}
				rls= mapLookup(requestRegex, map);
			}
			if(rls!=null){
				
				Iterator <String> it = rls.iterator();
				while (it.hasNext()){
					String requestFile=it.next();
					String expectedRespName="";
					String expectedNativeRequestName="";
					String expectedNativeResponseName="";
					String expectedRequestHeaderName="";
					String expectedResponseHeaderName="";
					String expectedNativeRequestHeaderName="";
					String expectedNativeResponseHeaderName="";
					IData test=IDataFactory.create();
					IDataCursor testCursor = test.getCursor();
					//map Request file and URL
					IDataUtil.put( testCursor, "ReqFileName", requestFile );
					IDataUtil.put( testCursor, "ReqFileURL", map.get(requestFile));	
					//map Response file and URL
					expectedRespName=requestFile.replace("_Request.", "_Response.");
					String responseFileURL=map.get(expectedRespName);
					if(responseFileURL != null){
					
					IDataUtil.put( testCursor, "ResponseFileName", expectedRespName );
					IDataUtil.put( testCursor, "ResponseFileURL",responseFileURL );
					}
					//map RequestNumber, OperationName and HTTPMethod
					String expectedRespName1=expectedRespName.replaceAll(filter+"_", "");
					String[] operationDetail=expectedRespName1.split("_");
					
					IDataUtil.put( testCursor, "RequestNumber", operationDetail[2] );
					IDataUtil.put( testCursor, "OperationName", operationDetail[0] );
					IDataUtil.put( testCursor, "HTTPMethod", operationDetail[1] );
					//map Native Request file and URL
					expectedNativeRequestName=requestFile.replace("_Request.", "_NativeRequest.");
					if(apiType.equals("SOAP")){
						
						expectedNativeRequestName=expectedNativeRequestName.replaceAll("(json|xml)", "xml");
					}
					String nativeRequestFileURL=map.get(expectedNativeRequestName);
					if(nativeRequestFileURL != null){
						IDataUtil.put( testCursor, "ReqNativeFileName", expectedNativeRequestName );
						IDataUtil.put( testCursor, "ReqNativeFileURL",nativeRequestFileURL );
						}
					//map Native Response file and URL
					expectedNativeResponseName=requestFile.replace("_Request.", "_NativeResponse.");
					if(apiType.equals("SOAP")){
						
						expectedNativeResponseName=expectedNativeResponseName.replaceAll("(json|xml)", "xml");
					}
					String nativeResponseFileURL=map.get(expectedNativeResponseName);
					if(nativeResponseFileURL != null){
						IDataUtil.put( testCursor, "RespNativeFileName", expectedNativeResponseName );
						IDataUtil.put( testCursor, "RespNativeFileURL",nativeResponseFileURL );
						}
					//map  Request Header file and URL
					expectedRequestHeaderName=requestFile.replace("_Request.", "_RequestHeader.");
					if(apiType.equals("SOAP")){
						
						expectedRequestHeaderName=expectedRequestHeaderName.replaceAll("(json|xml)", "json");
					}
					String requestHeaderFileURL=map.get(expectedRequestHeaderName);
					if(requestHeaderFileURL != null){
						IDataUtil.put( testCursor, "ReqHeaderFileName", expectedRequestHeaderName );
						IDataUtil.put( testCursor, "ReqHeaderFileURL",requestHeaderFileURL );
						}
					
					//map  Response Header file and URL
					
					expectedResponseHeaderName=requestFile.replace("_Request.", "_ResponseHeader.");
					if(apiType.equals("SOAP")){
						
						expectedResponseHeaderName=expectedResponseHeaderName.replaceAll("(json|xml)", "json");
					}
					String responseHeaderFileURL=map.get(expectedResponseHeaderName);
					if(responseHeaderFileURL != null){
						IDataUtil.put( testCursor, "RespHeaderFileName", expectedResponseHeaderName );
						IDataUtil.put( testCursor, "RespHeaderFileURL",responseHeaderFileURL );
						}
					//map  Native Request Header file and URL
					
					expectedNativeRequestHeaderName=requestFile.replace("_Request.", "_NativeRequestHeader.");
					if(apiType.equals("SOAP")){
						
						expectedNativeRequestHeaderName=expectedNativeRequestHeaderName.replaceAll("(json|xml)", "json");
					}
					String requestNativeHeaderFileURL=map.get(expectedNativeRequestHeaderName);
					if(requestNativeHeaderFileURL != null){
						IDataUtil.put( testCursor, "ReqNativeHeaderFileName", expectedNativeRequestHeaderName );
						IDataUtil.put( testCursor, "ReqNativeHeaderFileURL",requestNativeHeaderFileURL );
						}
					//map  Native Response Header file and URL
					expectedNativeResponseHeaderName=requestFile.replace("_Request.", "_NativeResponseHeader.");
					if(apiType.equals("SOAP")){
						
						expectedNativeResponseHeaderName=expectedNativeResponseHeaderName.replaceAll("(json|xml)", "json");
					}
					String responseNativeHeaderFileURL=map.get(expectedNativeResponseHeaderName);
					if(responseNativeHeaderFileURL != null){
						IDataUtil.put( testCursor, "RespNativeHeaderFileName", expectedNativeResponseHeaderName );
						IDataUtil.put( testCursor, "RespNativeHeaderFileURL",responseNativeHeaderFileURL );
						}
					
					testCursor.destroy();
					reqTestList.add(test);
					expectedRespName="";
					System.out.print(expectedRespName);
				}
			}
				
		
			
		
			if(reqTestList.size()>0)
			{
				IData[] arr = reqTestList.toArray(new IData[0]);
				IDataCursor pipelineCursor_1 = pipeline.getCursor();
				IDataUtil.put( pipelineCursor_1, "testData", arr);
				pipelineCursor_1.destroy();		
			}	
		// --- <<IS-END>> ---

                
	}



	public static final void checkFileExists (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(checkFileExists)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fileName
		// [o] field:0:required fileExists
		String fileExists= null;
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	fileName = IDataUtil.getString( pipelineCursor, "fileName" );
		pipelineCursor.destroy();
		 File f = new File(fileName);
		 
		    // Checking if the specified file exists or not
		    if (f.exists())
		 
		        //  if the file exists
		    	fileExists="true";
		    else
		 
		        //  if the file does not exists
		    	fileExists="false";
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "fileExists", fileExists );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void compareJSON (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compareJSON)>> ---
		// @sigtype java 3.5
		// [i] field:0:required actualStr
		// [i] field:0:required compareStr
		// [i] field:1:required ignoreTags
		// [o] field:0:required result
		// [o] field:0:required errMessage
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	actualStr = IDataUtil.getString( pipelineCursor, "actualStr" );
			String	compareStr = IDataUtil.getString( pipelineCursor, "compareStr" );
			String[]	ignoreTags = IDataUtil.getStringArray( pipelineCursor, "ignoreTags" );
		pipelineCursor.destroy();
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		try{
		if(ignoreTags!=null)
		{
			List<Customization> customizations = new ArrayList<>(ignoreTags.length);
		
				for (String ignoreTag : ignoreTags) {
					
						customizations.add(new Customization("**."+ignoreTag,(o1, o2) -> true));
					}
				JSONAssert.assertEquals(actualStr, compareStr,
						new CustomComparator(JSONCompareMode.STRICT, customizations.toArray(new Customization[0])));
		}
		else
			{
				JSONAssert.assertEquals(actualStr, compareStr, true);
			}
		
			IDataUtil.put( pipelineCursor_1, "result", "Success" );
		} catch (Exception e) {
			IDataUtil.put( pipelineCursor_1, "result", "Failed" );
			IDataUtil.put( pipelineCursor_1, "errMessage", e.getMessage() );
		}
		
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void compareXML (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compareXML)>> ---
		// @sigtype java 3.5
		// [i] field:0:required actualXmlStr
		// [i] field:0:required expectedXMLStr
		// [i] field:0:required ignoreTags
		// [i] field:1:required ignoreTagList
		// [o] field:0:required result
		// [o] field:0:required errMessage
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	actualXmlStr = IDataUtil.getString( pipelineCursor, "actualXmlStr");
			String	expectedXMLStr = IDataUtil.getString( pipelineCursor, "expectedXMLStr" );			
			String[]	ignoreTagList = IDataUtil.getStringArray( pipelineCursor, "ignoreTagList" );
		pipelineCursor.destroy();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		try{
			if(ignoreTagList!=null)
			{
				List<String> customizations = new ArrayList<>(ignoreTagList.length);
				for (String ignoreTag : ignoreTagList) {
						customizations.add(ignoreTag);
				    }
				
				Diff myDiffSimilar = DiffBuilder.compare(expectedXMLStr).
						withTest(actualXmlStr).withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
						.withNodeFilter(node -> !customizations.contains(node.getNodeName()))
						.ignoreComments().normalizeWhitespace().checkForSimilar().build();
				if("[identical]".equalsIgnoreCase(myDiffSimilar.toString()))
					IDataUtil.put( pipelineCursor_1, "result", "Success" );
			else
			{
				IDataUtil.put( pipelineCursor_1, "result", "Failed" );
				IDataUtil.put( pipelineCursor_1, "errMessage", myDiffSimilar.toString());
			}
		}
			else
			{
				Diff myDiffSimilar = DiffBuilder.compare(expectedXMLStr).
						withTest(actualXmlStr).withNodeMatcher(new DefaultNodeMatcher(ElementSelectors.byName))
						.ignoreComments().normalizeWhitespace().checkForSimilar().build();
				if("[identical]".equalsIgnoreCase(myDiffSimilar.toString()))
					IDataUtil.put( pipelineCursor_1, "result", "Success" );
			else
			{
				IDataUtil.put( pipelineCursor_1, "result", "Failed" );
				IDataUtil.put( pipelineCursor_1, "errMessage", myDiffSimilar.toString());
			}
			}
			
			
		/*
		 * List<String> customizations = new ArrayList<>(ignoreTagList.length); for
		 * (String ignoreTag : ignoreTagList) { customizations.add(ignoreTag); }
		 * 
		 * Diff myDiffSimilar = DiffBuilder.compare(expectedXMLStr).
		 * withTest(actualXmlStr).withNodeMatcher(new
		 * DefaultNodeMatcher(ElementSelectors.byName)) .withNodeFilter(node ->
		 * !customizations.contains(node.getNodeName()))
		 * .ignoreComments().normalizeWhitespace().checkForSimilar().build();
		 * 
		 * if("[identical]".equalsIgnoreCase(myDiffSimilar.toString())) IDataUtil.put(
		 * pipelineCursor_1, "result", "Success" ); else { IDataUtil.put(
		 * pipelineCursor_1, "result", "Failed" ); IDataUtil.put( pipelineCursor_1,
		 * "errMessage", myDiffSimilar.toString()); }
		 */
		
		pipelineCursor_1.destroy();
		}
		catch(Exception e)
		{
		IDataUtil.put( pipelineCursor_1, "result", "Failed" );
		IDataUtil.put( pipelineCursor_1, "errMessage", e.getMessage());
		}
		// --- <<IS-END>> ---

                
	}



	public static final void getDocumentNames (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getDocumentNames)>> ---
		// @sigtype java 3.5
		// [i] record:0:required paths
		// [o] field:1:required path
		ArrayList<String> a =new ArrayList<String>();
		String[]	path= null;
		// pipeline
		 IDataMap pipelineMap = new IDataMap(pipeline);
		 IData paths = pipelineMap.getAsIData("paths");
		if (null==paths){} else{
		 IDataCursor pic=paths.getCursor();
		
		while(pic.hasMoreData()){
		pic.next();
			a.add(pic.getKey());
		}
		}
		if(a.isEmpty()){} else{
		path=a.stream().toArray(String[]::new);}
		pipelineMap.put("path", path);
		// --- <<IS-END>> ---

                
	}



	public static final void getResourcesAndMethods (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getResourcesAndMethods)>> ---
		// @sigtype java 3.5
		// [i] record:0:required paths
		// [o] record:1:required resources
		// [o] - field:0:required resourceName
		// [o] - field:1:required resourceMethods
		List<IData> resList=new ArrayList<IData>();
		IData[] arr= null;
		String methods="post,patch,put,get,delete";
		// pipeline
		 IDataMap pipelineMap = new IDataMap(pipeline);
		 IData paths = pipelineMap.getAsIData("paths");
		if (null==paths){} else{
		 IDataCursor pic=paths.getCursor();
		
		while(pic.hasMoreData()){
		pic.next();
			
			IData test=IDataFactory.create();
			IDataCursor testCursor = test.getCursor();
			IDataUtil.put( testCursor, "resourceName", pic.getKey() );
			ArrayList<String> met =new ArrayList<String>();
			IDataMap pathsMap = new IDataMap(paths);
			IData res = pathsMap.getAsIData(pic.getKey());
			if (null==res){} else{
				
				IDataCursor methcurser=res.getCursor();
				while(methcurser.hasMoreData()){
					methcurser.next();
					int index1=methods.indexOf(methcurser.getKey());
					if(index1 > -1 ){
						met.add(methcurser.getKey());
					}
				}
				if(met.isEmpty()){} else{
					String [] resMethods=met.stream().toArray(String[]::new);
					IDataUtil.put(testCursor,"resourceMethods",resMethods);
				}
				
			}
			testCursor.destroy();
			resList.add(test);
			
		}
		}
		if(resList.isEmpty()){} else{
			arr = resList.toArray(new IData[0]);
		
		
		}
		pipelineMap.put("resources", arr);
			
		// --- <<IS-END>> ---

                
	}



	public static final void getURLComponents (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getURLComponents)>> ---
		// @sigtype java 3.5
		// [i] field:0:required url
		// [i] field:0:optional resource
		// [o] field:0:required host
		// [o] field:0:required path
		// [o] field:0:optional query
		// [o] field:0:required trxResource
		String protocal=null;
		String host=null;
		String port=null;
		String path=null;
		String query=null;
		String regexStr=null;
		String resource1=null;
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String url = IDataUtil.getString( pipelineCursor, "url" );
		String	resource = IDataUtil.getString( pipelineCursor, "resource" );
		pipelineCursor.destroy();
		try {
		URL aURL = new URL(url);
		host=aURL.getHost();
		path=aURL.getPath();
		port=String.valueOf(aURL.getPort());
		protocal=aURL.getProtocol();
		query=aURL.getQuery();
		regexStr=resource.replaceAll("\\/", "\\\\/");
		regexStr=regexStr.replaceAll("[{]\\w+[}]", ".*");
		Pattern pattern = Pattern.compile(regexStr);
		Matcher matcher = pattern.matcher(path);
		if (matcher.find())
		{
			resource1=matcher.group(0);
		}
		} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		/*
		String [] actRes=resource.split("/");
		String [] urlRes=resource1.split("/");
		 for(int i = 0 ; i < actRes.length ; ++i) {
			 String s=actRes[i];
			 
		  } */
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "protocal", protocal );
		IDataUtil.put( pipelineCursor_1, "host", host );
		IDataUtil.put( pipelineCursor_1, "port", port );
		IDataUtil.put( pipelineCursor_1, "path", path );
		IDataUtil.put( pipelineCursor_1, "query", query );
		IDataUtil.put( pipelineCursor_1, "trxResource", resource1 );
		// pathParams
		IData	pathParams = IDataFactory.create();
		IDataUtil.put( pipelineCursor_1, "pathParams", pathParams );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void multiConcat (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(multiConcat)>> ---
		// @sigtype java 3.5
		// [i] field:0:required req1
		// [i] field:0:required req2
		// [i] field:0:required req3
		// [i] field:0:required req4
		// [i] field:0:required req5
		// [i] field:0:required req6
		// [i] field:0:required req7
		// [i] field:0:required req8
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	req1 = IDataUtil.getNonEmptyString(pipelineCursor, "req1");
			String	req2 = IDataUtil.getNonEmptyString(pipelineCursor, "req2");
			String	req3 = IDataUtil.getNonEmptyString(pipelineCursor, "req3");
			String	req4 = IDataUtil.getNonEmptyString(pipelineCursor, "req4");
			String	req5 = IDataUtil.getNonEmptyString(pipelineCursor, "req5");
			String	req6 = IDataUtil.getNonEmptyString(pipelineCursor, "req6");
			String	req7 = IDataUtil.getNonEmptyString(pipelineCursor, "req7");
			String	req8 = IDataUtil.getNonEmptyString(pipelineCursor, "req8");
			String result=null;
			if(req1 !=null || req2 !=null || req3 !=null || req4 !=null  || req5 !=null || req6 !=null || req7 !=null || req8 !=null){
				result= req1+req2+req3+req4+req5+req6+req7+req8;
			}
			
			pipelineCursor.destroy();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void uncompressString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(uncompressString)>> ---
		// @sigtype java 3.5
		// [i] field:0:required compressedString
		// [o] field:0:required unCompressedString
		// [o] field:0:required error
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	compressedString = IDataUtil.getString( pipelineCursor, "compressedString" );
		pipelineCursor.destroy();
		String uc= null;
		String error= null;
		try{
			uc=uncompressString(compressedString);
		}catch(Exception e){
			error= e.getLocalizedMessage().toString();
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "unCompressedString", uc );
		IDataUtil.put( pipelineCursor_1, "error", error );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}



	public static final void writeBytesToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeBytesToFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fileName
		// [i] object:0:required bytes
		// [o] field:0:required writeStatus
		// [o] field:0:required errorMessage
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	fileName = IDataUtil.getString( pipelineCursor, "fileName" );
			byte[] strToBytes=(byte[])(IDataUtil.get( pipelineCursor, "bytes" ));
		pipelineCursor.destroy();
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		try {
				FileOutputStream outputStream = new FileOutputStream(fileName);	
				outputStream.write(strToBytes);
				outputStream.close();
				IDataUtil.put(pipelineCursor_1, "writeStatus", "Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			IDataUtil.put(pipelineCursor_1, "errorMessage", e.getLocalizedMessage());
			IDataUtil.put(pipelineCursor_1, "writeStatus", "Failed");
		}
		
		
		pipelineCursor_1.destroy();
		
		// pipeline
			
		// --- <<IS-END>> ---

                
	}



	public static final void writeToFile (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(writeToFile)>> ---
		// @sigtype java 3.5
		// [i] field:0:required fileName
		// [i] field:0:required inputStr
		// [o] field:0:required writeStatus
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	fileName = IDataUtil.getString( pipelineCursor, "fileName" );
			String	inputStr = IDataUtil.getString( pipelineCursor, "inputStr" );
		pipelineCursor.destroy();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		
		File f=new File(fileName);
		FileWriter fw;
		BufferedWriter bw;
		
		try{
			if(!f.exists())
			{
				   f.createNewFile();
			}		
			fw = new FileWriter(f.getAbsolutePath(),true);
			bw = new BufferedWriter(fw);
			bw.write(inputStr);
			bw.close();
			IDataUtil.put( pipelineCursor_1, "writeStatus", "Success" );
		}
		catch(Exception ex)
		{
			IDataUtil.put( pipelineCursor_1, "writeStatus", "Failed" );
		}
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static String uncompressString(String zippedBase64Str)  throws IOException {
		String unCompressedPayload = null;
		byte[] bytes = Base64.getDecoder().decode(zippedBase64Str);
		GZIPInputStream zi = null;
		try{
		zi = new GZIPInputStream(new ByteArrayInputStream(bytes)); 
		unCompressedPayload = IOUtils.toString(zi);
		}finally{
		IOUtils.closeQuietly(zi); 
		}
		return unCompressedPayload;
		}
	
	
	
	
	static  List<String> mapLookup(String regex, HashMap<String, String> inputmap ){
			Pattern pattern = Pattern.compile(regex);
			 
			 List<String> values  = inputmap.keySet().stream().filter(string -> pattern.matcher(string).matches()).collect(Collectors.toList());
			if(values!= null && !values.isEmpty()){
		      return  values;
		
		   }
			
			return new ArrayList<>();
		}
	// --- <<IS-END-SHARED>> ---
}

