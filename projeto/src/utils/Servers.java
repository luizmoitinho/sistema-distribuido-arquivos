package utils;

import java.util.ArrayList;

public class Servers {
	ArrayList<Server> servers;
	
	public Servers() {
		this.servers = new ArrayList<Server>();
	}
	
	public  ArrayList<Server> clean() {
		this.servers = new ArrayList<Server>();
		return this.servers;
	}
	
	public Server add(Server newServer) {
		this.servers.add(newServer);
		return newServer;
	}
	
	public ArrayList<Server> getAll(){
		return this.servers;
	}
	
	
	public Server get(int index){
		return this.servers.get(index);
	}
	
	
}
