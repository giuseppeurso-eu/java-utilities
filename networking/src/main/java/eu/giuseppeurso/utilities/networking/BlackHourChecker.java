package eu.giuseppeurso.utilities.networking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.giuseppeurso.utilities.generics.filesystem.FileOperations;
import eu.giuseppeurso.utilities.generics.parser.JsonUtils;

public class BlackHourChecker {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss.SSS");
	private static File itemsStore;
	private static String itemsDir = "tmp/blackhour";
	private static String urlEpriceProducts = "http://....";
	
	/**
	 * Main executor.
	 * @param args
	 */
	public static void main(String[] args) {
		createItemsStore();
		JSONObject itemsJson = getItems();
		stripItems(itemsJson, args[0]);
	}
	
	/**
	 *  The ePrice products retriever.
	 * @return
	 */
	private static JSONObject getItems(){
//		String filePath = "src/main/resources/files/test-file-04.json";
//		String response =  JsonUtils.jsonFileToString(filePath);
		System.out.println("Retrieving ePrice products from url: "+urlEpriceProducts);
		String response = HttpStuff.getUrlAsString(urlEpriceProducts);		
		JSONObject respJ = JsonUtils.stringObjectToJsonObject(response);
		return respJ;
	}

	/**
	 * Reads the origin json, extracts some info and saves data to the file system. 
	 * @param items to be stripped
	 * @param dateToCheck example "2017-11-16"
	 */
	private static void stripItems(JSONObject items, String dateToCheck){
		JSONObject repository = JsonUtils.jsonFileToJSONObject(itemsStore.getAbsolutePath());
		JSONObject repositoryUpdated = new JSONObject();
		
		JSONArray itemsArray = repository.getJSONArray("items");
		JSONArray docArray = items.getJSONArray("documents");
		Boolean notify = false;
		for (int i = 0; i < docArray.length(); i++) {
			JSONObject currentPublishedProduct = docArray.getJSONObject(i).getJSONObject("fields");
			String productName = currentPublishedProduct.getJSONObject("name").getString("stringValue");
			String productSku = currentPublishedProduct.getJSONObject("sku").getString("integerValue");
			String productCreated = docArray.getJSONObject(i).getString("createTime");
			String productUpdated = docArray.getJSONObject(i).getString("updateTime");
			String productIsBlack = currentPublishedProduct.getJSONObject("isBlackHour").getString("integerValue");
			if (productCreated.contains(dateToCheck)) {
				JSONObject newItemJson = new JSONObject();
				if (!isSaved(itemsArray, productSku)) {
					newItemJson.put("id", productSku);
					newItemJson.put("name", productName);
					newItemJson.put("isBlack", productIsBlack);
					newItemJson.put("createTime", productCreated);
					newItemJson.put("updateTime", productUpdated);
					System.out.println(sdf.format(new Date())+" - New item detected: [ID="+productSku+", CREATION_DATE="+productCreated+", IS_BLACK="+productIsBlack+"]");
					itemsArray.put(newItemJson);
					notify=true;
				} else {
					System.out.println(sdf.format(new Date())+" - Item already saved with id: "+productSku);
				}
			}
		}
		
		// Update the items store file
		repositoryUpdated.put("items", itemsArray);
		repositoryUpdated.put("totalResults", itemsArray.length());
		try {
			FileOperations.saveTextFile(itemsStore, repositoryUpdated.toString(1));
			System.out.println(sdf.format(new Date())+" - File updated: "+itemsStore.getAbsolutePath());
			if (notify) {
				String emalBody = "Rilevati nuovi prodotti BLACKHOUR per la data: "+dateToCheck+". Verifica su: https://www.eprice.it/black-hour";
				emalBody = emalBody+"\n\n "+repositoryUpdated.toString(1);
				Email.sendMailTLS("mawdev4@gmail.com", "marmellata15", true, true, "smtp.gmail.com", "587", "mawdev4@gmail.com", "urso.giu@gmail.com", "BlackHour notification", emalBody);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Final json: "+repoUpdatedJson.toString(1));		
	}
	
	/**
	 * Saves new discovered items into a file.
	 */
	private static void createItemsStore(){
		Date today = new Date();
		DateFormat dateFormat  = new SimpleDateFormat("YYYY-MM-dd");
		String todayDir = dateFormat.format(today);
		String dirPath = itemsDir+"-"+todayDir;
		String filename = "newItems-"+todayDir+".json";
		
		File  repository = new File(dirPath);
		if (!repository.exists()) {
			repository.mkdir();
			System.out.println(sdf.format(new Date())+" - Directroy created: "+repository.getAbsolutePath());
		}
		File itemsFile = new File(dirPath+"/"+filename);
		if (!itemsFile.exists()) {
			try {
				itemsFile.createNewFile();
				FileOperations.saveTextFile(itemsFile, "{\"items\":[]}");
				System.out.println(sdf.format(new Date())+" - File created: "+itemsFile.getAbsolutePath());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		itemsStore= itemsFile;
	}
	
	/**
	 * Checks if an item is already saved into the repository.
	 * @param jArray
	 * @param idToCheck
	 * @return
	 */
	private static Boolean isSaved(JSONArray jArray, String idToCheck){
		Boolean exists = false;
		if (jArray.length()>0) {
			for (int j = 0; j < jArray.length(); j++) {
				JSONObject savedItem = jArray.getJSONObject(j);
				if (savedItem.get("id").equals(idToCheck)) {
					exists = true;
				}
			}
		}
		return exists ;
	}
}
