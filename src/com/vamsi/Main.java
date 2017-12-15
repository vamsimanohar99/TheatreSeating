package com.vamsi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		int totalSeats = 0;
		int maxSeats = 0;
		System.out.println("Input:");
		System.out.println("Please enter number of rows for a theatre layout:");
		Scanner scanner = new Scanner(System.in);
		List<Row> Rows = new ArrayList<Row>();
		List<Section> sections;
		int n = Integer.parseInt(scanner.nextLine());
		System.out.println("Please enter theatre layout where each row is made up of" + "\n"
				+ "1 or more sections separated by a space.");
		for (int i = 0; i < n; i++) {
			sections = new ArrayList<Section>();
			Row r = new Row();
			r.setRowid(i + 1);
			String line = scanner.nextLine();
			String[] numberStrs = line.split(" ");
			for (int j = 0; j < numberStrs.length; j++) {
				Section s = new Section();
				s.setSectionid(j + 1);
				s.setNumOfSeats(Integer.parseInt(numberStrs[j]));
				maxSeats = maxSeats > Integer.parseInt(numberStrs[j]) ? maxSeats : Integer.parseInt(numberStrs[j]);
				totalSeats = totalSeats + Integer.parseInt(numberStrs[j]);
				sections.add(s);
				// System.out.println(s.getSectionid()+" "+s.getNumOfSeats());

			}

			r.setSections(sections);
			// System.out.println(r.getRowid()+" row "+r.getSections().size());
			Rows.add(r);
			// sections.clear();

		}
		// System.out.println(totalSeats);
		/*
		 * for(Row r1: Rows) for(Section s:r1.getSections()) {
		 * System.out.println("RowID"+r1.getRowid());
		 * System.out.println("Section"+s.getSectionid()+" "+s.getNumOfSeats());
		 * }
		 */

		System.out.println(
				"Please enter ticket requests:" + "\n" + "name followed by a space and the number of requested tickets:"
						+ "\n" + "Please type \"end\" when done.");
		List<Customer> customers = new ArrayList<Customer>();
		while (true) {
			String line1 = scanner.nextLine();
			if (line1.equals("end")) {
				break;
			}
			String[] char1 = line1.split(" ");

			Customer c = new Customer();
			c.setCustomerName(char1[0]);
			c.setRequestedSeats(Integer.parseInt(char1[1]));
			customers.add(c);
		}
		scanner.close();

		System.out.println("\nOutput:\n");
		for (Customer c : customers) {
			int count = 0;
			for (Row r1 : Rows) {
				count++;

				for (Section s : r1.getSections()) {
					int seats = s.getNumOfSeats();

					if (c.getRequestedSeats() > 0 && seats == c.getRequestedSeats() && count <= 2) { 
						
						//trying to put parties as close to the front as possible by giving first two rows highest priority 
						
						System.out.println(
								c.getCustomerName() + " Row " + r1.getRowid() + " Section " + s.getSectionid());
						s.setNumOfSeats(s.getNumOfSeats() - c.getRequestedSeats());
						totalSeats = totalSeats - c.getRequestedSeats();
						c.setRequestedSeats(0);
					}

				}
			}
			for (Row r1 : Rows) {
				for (Section s : r1.getSections()) {
					int seats = s.getNumOfSeats();
					if (c.getRequestedSeats() > 0 && seats >= c.getRequestedSeats()) {
						System.out.println(
								c.getCustomerName() + " Row " + r1.getRowid() + " Section " + s.getSectionid());
						s.setNumOfSeats(s.getNumOfSeats() - c.getRequestedSeats());
						totalSeats = totalSeats - c.getRequestedSeats();
						c.setRequestedSeats(0);
					}

				}
			}
			if (c.getRequestedSeats() > 0 && maxSeats < c.getRequestedSeats() && totalSeats > c.getRequestedSeats()) {
				System.out.println(c.getCustomerName() + " Call to split party.");
			} else if (c.getRequestedSeats() > 0 && maxSeats < c.getRequestedSeats()
					&& totalSeats < c.getRequestedSeats()) {
				System.out.println(c.getCustomerName() + " Sorry, we can't handle your party.");
			}
			// System.out.println(totalSeats);
		}

	}
}
