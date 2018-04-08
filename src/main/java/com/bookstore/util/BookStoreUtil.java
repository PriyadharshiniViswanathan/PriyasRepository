/*
 * Project Name:BookStore
 * Author:Priyadharshini Viswanathan
 * Date Created : April 6,2018
 */
package com.bookstore.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bookstore.bean.Book;

/**
 * The Class BookStoreUtil.
 */
@Component
public class BookStoreUtil {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(BookStoreUtil.class);
	
	/** The source file path. */
	private static String sourceFilePath;
	
	/** The books. */
	private static List<Book> books;
	
	/** The book quantity. */
	private static Map<Book,Integer> bookQuantity;
	
	/** The Constant BOOK. */
	private static final String BOOK ="book";
	
	/** The Constant QUANTITY. */
	private static final String QUANTITY ="quantity";
	
	/**
	 * Sets the source file path.
	 *
	 * @param sourceFilePath the new source file path
	 */
	@Value("${sourcefile.path}")
	public void setSourceFilePath(String sourceFilePath) {
		BookStoreUtil.sourceFilePath = sourceFilePath;
	}

	/**
	 * Gets the books.
	 *
	 * @return the books
	 */
	public static List<Book> getBooks() {
		return books;
	}

	/**
	 * Sets the books.
	 *
	 * @param books the new books
	 */
	public static void setBooks(List<Book> books) {
		BookStoreUtil.books = books;
	}

	/**
	 * Gets the book quantity.
	 *
	 * @return the book quantity
	 */
	public static Map<Book, Integer> getBookQuantity() {
		return bookQuantity;
	}

	/**
	 * Sets the book quantity.
	 *
	 * @param bookQuantity the book quantity
	 */
	public static void setBookQuantity(Map<Book, Integer> bookQuantity) {
		BookStoreUtil.bookQuantity = bookQuantity;
	}

	/**
	 * Read Initial file data.
	 */
	public static void readFile() {
		logger.info("readFile start");
		BufferedReader bufferedReader = null;
		books = new ArrayList<Book>();
		bookQuantity = new HashMap<Book, Integer>();
		try {
			wrapClient();
			URL url = new URL(sourceFilePath);
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			if (null == inputStream) {
				logger.error("The Source file is not available in the path " + sourceFilePath);
				return;
			}
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String read = null;
			while ((read = bufferedReader.readLine()) != null) {
				Map<String,Object> spiltData = convertStringToBean(read);
				Book book = (Book)spiltData.get(BOOK);
				books.add(book);
				bookQuantity.put(book, (Integer) spiltData.get(QUANTITY));
			}
		} catch (Exception e) {
			logger.error("Exception occured while reading file ", e);
		} finally {
			try {
				bufferedReader.close();
			} catch (Exception e) {
				logger.error("Exception occured while closing reader", e);
			}
		}
		logger.info("readFile end");
	}
	
	/**
	 * Convert string to bean.
	 *
	 * @param inputStr the input str
	 * @return the map
	 * @throws ParseException the parse exception
	 */
	public static  Map<String, Object> convertStringToBean(String inputStr) throws ParseException{
		String[] splited = inputStr.split(";");
		Book book = new Book();
		book.setTitle(splited[0]);
		book.setAuthor(splited[1]);
		book.setPrice(getPrice(splited[2]));
		Map<String, Object> spiltData = new HashMap<String, Object>();
		spiltData.put(BOOK, book);
		spiltData.put(QUANTITY, new Integer(splited[3]));
		return spiltData;
	}
	
	/**
	 * Convert price from String to BigDecimal.
	 *
	 * @param price the price
	 * @return the price
	 * @throws ParseException the parse exception
	 */
	public static BigDecimal getPrice(String price) throws ParseException{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);
		BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(price);
		return bigDecimal;


	}
	
	/**
	 * to bypass SSL Handshake exception
	 *
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws KeyManagementException the key management exception
	 */
	private static void wrapClient() throws NoSuchAlgorithmException, KeyManagementException {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			}
			public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
			}
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		} };
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
	}

}
