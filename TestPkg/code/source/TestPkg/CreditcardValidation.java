package TestPkg;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.softwareag.util.IDataMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
// --- <<IS-END-IMPORTS>> ---

public final class CreditcardValidation

{
	// ---( internal utility methods )---

	final static CreditcardValidation _instance = new CreditcardValidation();

	static CreditcardValidation _newInstance() { return new CreditcardValidation(); }

	static CreditcardValidation _cast(Object o) { return (CreditcardValidation)o; }

	// ---( server methods )---




	public static final void StringToStringList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(StringToStringList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required sInput
		// [o] field:0:required sList
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	sInput = IDataUtil.getString( pipelineCursor, "sInput" );
			ArrayList<Object> chars = new ArrayList<>();
			//List<String> chars = new LinkedList<String>();
			for (char ch : sInput.toCharArray()) {
		        chars.add(ch);
		    }
		pipelineCursor.destroy();
		// pipeline
		//IDataCursor pipelineCursor_1 = pipeline.getCursor();
		//String[]	sList = new String[];
		//List<String> sList = new LinkedList<String>();
		IDataMap pipelineMap = new IDataMap(pipeline);
		
		//String str = chars.toString();
		//List<String> strList = Arrays.asList(str);
		//pipelineMap.put("sList", strList);
		pipelineMap.put("sList", chars.toString());
		// --- <<IS-END>> ---

                
	}



	public static final void evenOddNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(evenOddNumber)>> ---
		// @sigtype java 3.5
		// [i] field:0:required number
		// [o] field:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	number = IDataUtil.getString( pipelineCursor, "number" );
			Integer num=Integer.parseInt(number);
			String result;
			if (num % 2 == 0) {result="even";}
			else {
				result="odd";
		    }
		pipelineCursor.destroy();
		
		// pipeline
		//String evenNum=Integer.toString(even);
		//String oddNum=Integer.toString(odd);
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		//IDataUtil.put( pipelineCursor_1, "odd", oddNum );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void mod10Service (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(mod10Service)>> ---
		// @sigtype java 3.5
		// [i] field:0:required finalIn
		// [o] field:0:required status
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	finalIn = IDataUtil.getString( pipelineCursor, "finalIn" );
			Integer num=Integer.parseInt(finalIn);
			String status;
			if (num % 10 == 0) {status="**********Validated Credit Card is Valid and Ready to Use************";}
			else {
				status="**********InActive Credit Card, PLease check from bank if it has been disabled or Permanently InActive*********";
		    }
			
		pipelineCursor.destroy();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "status", status );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void reverseNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(reverseNumber)>> ---
		// @sigtype java 3.5
		// [i] field:0:required number
		// [o] field:0:required reverse
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	number = IDataUtil.getString(pipelineCursor, "number");
		//int numbers=Integer.parseInt(number);
		long num=Long.parseLong(number); 
		long reverse = 0;  
		while(num != 0)   
		{  
		long remainder = num % 10;  
		reverse = reverse * 10 + remainder;  
		num = num/10;  
		} 
		pipelineCursor.destroy();
		// pipeline
		String reverseS=Long.toString(reverse);
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "reverse", reverseS);
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}
}

