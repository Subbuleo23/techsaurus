/*
 * The contents of this file, as updated from time to time by Dublin Core
 * Metadata Initiative (DCMI), are subject to the Dublin Core Public
 * License Version 1.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a current copy of the
 * License at http://dublincore.org/dcpl/.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Dublin Core Metadata Initiative. For
 * more information on the Dublin Core Metadata Initiative, please see
 * http://dublincore.org/.
 *
 * This is Original Code.
 *
 * The Developer of the Original Code is:
 *  H.Wagner    <wagnerh@oclc.org>
 * 
 * Portions created by DCMI are Copyright (C) 2000. 
 *
 */

package services;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.rpc.server.*;
import javax.xml.rpc.ServiceException;
import org.apache.axis.MessageContext;

import com.hp.hpl.jena.db.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.rdf.arp.JenaReader;

import java.io.*;
import java.util.*;
import java.text.BreakIterator;
import java.sql.Connection;
import java.sql.DriverManager;


public class VocabularyTerms extends remoteAxisServlet {

	protected MessageContext msgContext;
	
	public void init(java.lang.Object context) throws ServiceException {
		super.init(context);
		ServletEndpointContext ctx = (ServletEndpointContext) context;
		msgContext = (MessageContext) ctx.getMessageContext();		
	}
	
	public String listVocabularyTerms () {
		
		CharArrayWriter caw = new CharArrayWriter();
		String tmpstr = null;
		IDBConnection conn = null;
		
		try {		
			Class.forName(JDBCDriver);
			conn = new DBConnection(db_uri, db_user, db_password, db_type);
			ModelRDB rdbModel = ModelRDB.open(conn, registry_model);
			Model summaryModel = ModelFactory.createDefaultModel();
			
			RDFNode obj = summaryModel.createResource("http://dublincore.org/usage/documents/principles/#vocabulary-term");
			ArrayList rList = new ArrayList();
			String term = null;
			String termname = null;
			ResIterator ri = rdbModel.listSubjectsWithProperty(DC.type, obj);
			while (ri.hasNext()) {
				term = ri.next().toString();
				termname = term.substring(term.lastIndexOf("/")+1);
				rList.add(termname + "*" + term);
			}
			
			Collections.sort(rList);
			Bag refs = summaryModel.createBag();
			int itemCount = 0;
			for (int i=0; i < rList.size(); i++) {
				String s = (String) rList.get(i);
				Resource r1 = summaryModel.createResource(s.substring(s.lastIndexOf("*")+1));
				refs.add(r1);
				itemCount++;
			}
			
			Resource subject = summaryModel.createResource("http://dublincore.org/dcregistry/");
			subject.addProperty(DCTerms.references, refs);

			rdbModel.close();
			summaryModel.write(caw, "RDF/XML");
			summaryModel.close();
			
			String IP = getClientIP(msgContext);
			logger.info(IP);
		
			tmpstr = new String(caw.toString().getBytes("UTF8"), "UTF8");
						
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (Exception e2) { }
		}

		return tmpstr;		
	}
	
}
