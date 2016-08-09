package com.haniokasai.nukkit.SPAMBAN;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.plugin.PluginBase;



public class Main extends PluginBase implements Listener{
	static Map<String, Integer> lc = new HashMap<String, Integer>();
	static Map<String, Integer> spam = new HashMap<String, Integer>();
	static int ctime =(int) (System.currentTimeMillis()/1000);


	public void onEnable() {
		 this.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void onmotion(PlayerChatEvent event){
		Player player = event.getPlayer();
		String name   = player.getName();
		ctime = (int) (System.currentTimeMillis()/1000);
		if(lc.containsKey(name)){
			if(!spam.containsKey(name)){
				spam.put(name,0);
				lc.put(name,ctime);
			}else if((ctime - lc.get(name))>5){
				spam.remove(name);
				lc.put(name,ctime);
			}else if((ctime - lc.get(name))<=3){
				int i = spam.get(name)+1;
				spam.put(name,i);
				lc.put(name,ctime);
			}
			if(spam.containsKey(name)){
			if(spam.get(name)>15){
				String reason = " [SPAMBAN] "+name+" was banned.";
				String ip = player.getAddress();
				Server.getInstance().broadcastMessage(" [SPAMBAN] "+name+" was banned."+ip);
				Server.getInstance().getIPBans().addBan(ip,"ChatBan", null,reason);//ip-ban
				player.kick(" [SPAMBAN] "+name+" was banned."+ip);
			}
			}
		}else{
			lc.put(name,ctime);
		}
	}
}
