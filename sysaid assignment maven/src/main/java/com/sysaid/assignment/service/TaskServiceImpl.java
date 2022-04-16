package com.sysaid.assignment.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sysaid.assignment.domain.Task;

@Service
public class TaskServiceImpl implements  ITaskService{


	static Map<String,LinkedList<String>> taskUsersCompleteMap = new HashMap<String,LinkedList<String>>();
	static Map<String,LinkedList<String>> taskUsersWishMap = new HashMap<String,LinkedList<String>>();
	static LinkedList<String> usrListCompleteTask = new LinkedList<String>();
	static LinkedList<String> usrListWish = new LinkedList<String>();
	static Map<String,Integer> tasksRates = new HashMap<String,Integer>();
	static Map<String,LinkedList<Task>> userTaskMap = new HashMap<String,LinkedList<Task>>();



	@Value("${external.boredapi.baseURL}")
	private String baseUrl;
	//	private LinkedList<Task> taskList;

	public ResponseEntity<Task> getRandomTask() {
		String endpointUrl = String.format("%s/activity", baseUrl);
		RestTemplate template = new RestTemplate();
		ResponseEntity<Task> responseEntity = template.getForEntity(endpointUrl, Task.class);
		//		taskList.add(responseEntity.getBody());
		return responseEntity;
	}

	public void setTaskCompleted(String task_key, String user) {
		usrListCompleteTask =taskUsersCompleteMap.get(task_key);
		usrListCompleteTask.add(user);
		taskUsersCompleteMap.put(task_key,usrListCompleteTask);
		tasksRates.put(task_key, tasksRates.get(task_key)+2);
		System.out.println(taskUsersCompleteMap);

	}
	public void addToWishList(String task_key, String user) {
		usrListWish =taskUsersWishMap.get(task_key);
		usrListWish.add(user);
		taskUsersWishMap.put(task_key,usrListWish);
		tasksRates.put(task_key, tasksRates.get(task_key)+1);
		System.out.println(taskUsersWishMap);
	}



	public void fetchTasksByType(String user,String type) {

		String endpointUrl = String.format("%1$s/activity?type=%2$s", baseUrl,type);
		RestTemplate template = new RestTemplate();
		LinkedList<Task> taskList = new LinkedList<Task>(); 
		for(int tasksNumber=0;tasksNumber<10;tasksNumber++)
		{
			ResponseEntity<Task> responseEntity = template.getForEntity(endpointUrl, Task.class);
			taskList.add(responseEntity.getBody());
		}
		userTaskMap.put(user, taskList);
		initiateTaskUsersCompleteMap(userTaskMap);
		initiateTaskUsersWishMap(userTaskMap);
		System.out.println();
	}

	private void initiateTaskUsersWishMap(Map<String, LinkedList<Task>> userTaskMap) {
		for (Entry<String, LinkedList<Task>> entry : userTaskMap.entrySet()) {
			for(int taskNum=0;taskNum<10;taskNum++) {
				taskUsersWishMap.put(entry.getValue().get(taskNum).getKey(), new LinkedList<String>());
				tasksRates.put(entry.getValue().get(taskNum).getKey(),0);
				System.out.println(entry.getValue().get(taskNum).getKey());
			}
		}

	}

	private void initiateTaskUsersCompleteMap(Map<String, LinkedList<Task>> userTaskMap) {
		for (Entry<String, LinkedList<Task>> entry : userTaskMap.entrySet()) {
			for(int taskNum=0;taskNum<10;taskNum++) {
				taskUsersCompleteMap.put(entry.getValue().get(taskNum).getKey(), new LinkedList<String>());
				tasksRates.put(entry.getValue().get(taskNum).getKey(),0);
				System.out.println(entry.getValue().get(taskNum).getKey());
			}
		}

	}
}

