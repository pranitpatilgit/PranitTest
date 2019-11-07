package com.startup;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class PranitTest {
	
	public static void main(String[] args) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		System.out.println(cal.getTime());

		System.out.println(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		System.out.println(Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
		//System.out.println(Date.from(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toInstant(ZoneOffset.systemDefault()));
		
		System.out.println(formatDate(new Date()));
		
		LocalDate.now().isAfter(LocalDate.now());
		
		String a = "xyz [1] [2] [3] kljfkljd.";
		System.out.println(a.replaceAll("(\\[[\\d]*\\])", "P"));
		
		List<Long> list = new ArrayList<>();
		for (long i = 0; i < 100; i++) {
			list.add(i);	
		}
		
		list.parallelStream().forEach(id -> processActivity(id));
		
		//testXSL();
		
		String text = "aaa<p@gmail.com>a";
		//Pattern pattern = Pattern.compile("^(?!.*href).*<.*@.*>");
		Pattern pattern = Pattern.compile("<.*@.*>");
        Matcher matcher = pattern.matcher(text);
        System.out.println("pattern - "+matcher.matches());
	}
	
	public static String formatDate(Date date) {
		return formatDate(date, "dd-MM-yyyy");
	}
	
	public static String formatDate(Date date, String format) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.format(DateTimeFormatter.ofPattern(format));
	}
	
	private static void processActivity(long activityId) {
		System.out.println(activityId);
	}
	
	private static void testXSL() throws Exception {
		TransformerFactory factory = TransformerFactory.newInstance();
		InputStream stylesheet = new FileInputStream(new File("/home/pranit/git/Pranit/PranitTest/src/main/resources/html2plaintext.xsl"));
		Transformer transformer = factory.newTransformer(new StreamSource(stylesheet));
		
		String xml = "<p><p@gmail.com></p>";
		Source input = new StreamSource(new StringReader(xml));
		StringWriter writer = new StringWriter();
		transformer.transform(input, new StreamResult(writer));
		System.out.println(writer.toString());
	}
}
