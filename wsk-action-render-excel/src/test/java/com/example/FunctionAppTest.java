package com.example;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
		e1.name = "Foo";
		e1.birthDate = "1980-01-01";
		e1.bonus = 100d;
		e1.payment = 600d;
		p.employees.add(e1);

		Employee e2 = new Employee();
		e2.name = "Bar";
		e2.birthDate = "1970-01-01";
		e2.bonus = 200d;
		e2.payment = 1200d;
		p.employees.add(e2);

		JsonObject args = new Gson().toJsonTree(p).getAsJsonObject();
		System.out.println(new Gson().toJson(args));
		
		JsonObject response = FunctionApp.main(args);
		assertNotNull(response);
		String report = response.getAsJsonPrimitive("report").getAsString();

		new FileOutputStream(new File("target/generated.xls")).write(Base64.getDecoder().decode(report));

	}
}
