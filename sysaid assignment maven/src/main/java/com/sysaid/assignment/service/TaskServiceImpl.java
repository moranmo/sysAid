package com.sysaid.assignment.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sysaid.assignment.domain.Task;

@Service
public class TaskServiceImpl implements  ITaskService{
	HashSet<Task> taskList = new HashSet<Task>();

	static Map<String,LinkedList<String>> taskUsersCompleteMap = new HashMap<String,LinkedList<String>>();
	static Map<String,LinkedList<String>> taskUsersWishMap = new HashMap<String,LinkedList<String>>();
	static LinkedList<String> usrListCompleteTask = new LinkedList<String>();
	static LinkedList<String> usrListWish = new LinkedList<String>();
	static Map<String,Integer> tasksRates = new HashMap<String,Integer>();

	static {
		taskUsersCompleteMap.put("22",new LinkedList<String>());
		taskUsersCompleteMap.put("33",new LinkedList<String>());
		taskUsersWishMap.put("22",new LinkedList<String>());
		taskUsersWishMap.put("33",new LinkedList<String>());
		tasksRates.put("22",0);
		tasksRates.put("33",0);
	}


	@Value("${external.boredapi.baseURL}")
	private String baseUrl;


	public ResponseEntity<Task> getRandomTask() {
		String endpointUrl = String.format("%s/activity", baseUrl);
		RestTemplate template = new RestTemplate();
		ResponseEntity<Task> responseEntity = template.getForEntity(endpointUrl, Task.class);
		taskList.add(responseEntity.getBody());
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

	//	public fetchTasksByType() {

	//	}




}
