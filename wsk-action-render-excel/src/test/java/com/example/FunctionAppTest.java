package com.example;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

import org.junit.Test;

import com.example.FunctionApp.Employee;
import com.example.FunctionApp.Payload;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Unit test for simple function.
 */
public class FunctionAppTest {
	@Test
	public void testFunction() throws IOException {
		Payload p = new Payload();
		Employee e1 = new Employee();
		e1.setName("Foo");
		e1.setBirthDate("1980-01-01");
		e1.setBonus(100d);
		e1.setPayment(600d);
		p.getEmployees().add(e1);

		Employee e2 = new Employee();
		e2.setName("Bar");
		e2.setBirthDate("1970-01-01");
		e2.setBonus(200d);
		e2.setPayment(1200d);
		p.getEmployees().add(e2);

		JsonObject args = new Gson().toJsonTree(p).getAsJsonObject();
		System.out.println(new Gson().toJson(args));

		JsonObject response = FunctionApp.main(args);
		assertNotNull(response);
		String report = response.getAsJsonPrimitive("report").getAsString();

		try (OutputStream os = new FileOutputStream(new File("target/generated.xls"))) {
			os.write(Base64.getDecoder().decode(report));
		}
	}
}
