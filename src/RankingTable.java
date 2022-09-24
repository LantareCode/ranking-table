import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RankingTable {

	/*
	 	In this league:
	  		- a draw (tie) is worth 1 point 
	  		- win is worth 3 points. A loss is worth 0 points.

		If two or more teams have the same number of points, they should:
	 		- have the same rank and be printed in alphabetical order (as in the tie for 3rd place in the sample data).
	 */
	/**
	 * 
	 * @param args
	 */
	public static void main(String [] args) {
		
        // Get file from user
		Scanner scanner = getFile();
				
		// Build a map containing Team Name (String) and their Points (Integer)
		Map<String, Integer> teamPoints = new HashMap<>(); 
		while (scanner.hasNextLine()) {
			String data = scanner.nextLine();
			
			// Build map with TeamName, points
			String match[] = data.split(",");
			if (match.length <= 0) {
				continue;
			}
			teamPoints = updateTeamPoints(teamPoints, match[0].trim(), match[1].trim());
		}
		
		// Sort by points
		LinkedHashMap<String, Integer> sortedTeamPoints = new LinkedHashMap<>();
		teamPoints.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) 
		  						.forEachOrdered(x -> sortedTeamPoints.put(x.getKey(), x.getValue()));
		
		// Build result sheet.
		List<String> results = new ArrayList<String>();
		Integer ranking = 1;
		String previousName = null;
		Integer previousPoints = null;
		
		for (Map.Entry<String, Integer> pair : sortedTeamPoints.entrySet()) {
			String name = pair.getKey();
			Integer points = pair.getValue();
			System.out.println("name: " + name + " points: " + points);
			
			if (results.size() > 0 && points == previousPoints) {
				// Give the same rank as previous entry and re-order alphabetically if needed and
				if (name.compareToIgnoreCase(previousName) < 0) {
					results.set(results.size()-1, String.format("%d. %s, %d pts", ranking-1, name, points));
					results.add(String.format("%d. %s, %d pts", ranking-1, previousName, previousPoints));
				}else {
					results.add(String.format("%d. %s, %d pts", ranking-1, name, points));
				}
			} else {
				results.add(String.format("%d. %s, %d pts", ranking, name, points));
			}
			ranking = ranking+1;
			previousName = name;
			previousPoints = points;
		}
		
		/*
			TODO:
				- Test with same alphabet
				- Test with wrong entries
				- Improve readme.
				- Output results to .txt file + print on screen
		 */
		for (String str: results) {
			System.out.println(str);
		}
        
	}
	
	/**
	 * 
	 * @param teamPoints
	 * @param team  
	 * @return
	 */
	private static Map<String, Integer> updateTeamPoints (Map<String, Integer> teamPoints, String team1, String team2) {
	
		String team1Name = team1.substring(0, team1.lastIndexOf(" ")).trim();
		Integer team1Score = Integer.parseInt(team1.replace(team1Name, "").trim());
				
		String team2Name = team2.substring(0, team2.lastIndexOf(" ")).trim();
		Integer team2Score = Integer.parseInt(team2.replace(team2Name, "").trim());
					
		Integer team1Points = 0;
		Integer team2Points = 0;
		if (team1Score == team2Score) {
			team1Points = 1;
			team2Points = 1;
		} else if (team1Score > team2Score) {
			team1Points = 3;
		} else {
			team2Points = 3;
		}
		
		// Update team 1
		if (!teamPoints.containsKey(team1Name)) {
			teamPoints.put(team1Name, team1Points);
		} else {
			teamPoints.put(team1Name, (teamPoints.get(team1Name) + team1Points));
		}
		
		// Update team 2
		if (!teamPoints.containsKey(team2Name)) {
			teamPoints.put(team2Name, team2Points);
		} else {
			teamPoints.put(team2Name, (teamPoints.get(team2Name) + team2Points));
		}
		
		return teamPoints;
	}
	
	
	/**
	 * Prompt user for input & test if file location is valid
	 * @return
	 */
	private static Scanner getFile() {
		
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the path to your .txt file:");
        // String path = scanner.nextLine();
        String path = "D:/MyProjects/ranking-table/src/sample-input.txt";
        
        // Check if file is found.
        try {
        	scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
        	System.out.println(String.format("File at %s not found.", path));
        	getFile();
        }
        return scanner;
	}
}
