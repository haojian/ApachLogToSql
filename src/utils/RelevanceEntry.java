package utils;

public class RelevanceEntry {
	private int taskID;
	private String userid;
	private long viewedtime;
	private long judgedtime;
	private int relevanceRate;
	private int ifviewed;
	private int judgement;
	private String entryurl;
	
	public RelevanceEntry(){
		
	}
	
	
	public RelevanceEntry(int _taskID, String _userid, long _viewedtime, long _judgedtime,
			int _relevanceRate, int _ifviewed, int _judgement){
		taskID = _taskID;
		userid = _userid;
		viewedtime = _viewedtime;
		judgedtime = _judgedtime;
		relevanceRate = _relevanceRate;
		ifviewed = _ifviewed;
		judgement = _judgement;
	}
	
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public long getJudgedtime() {
		return judgedtime;
	}
	public void setJudgedtime(long judgedtime) {
		this.judgedtime = judgedtime;
	}
	public long getViewedtime() {
		return viewedtime;
	}
	public void setViewedtime(long viewedtime) {
		this.viewedtime = viewedtime;
	}
	public int getRelevanceRate() {
		return relevanceRate;
	}
	public void setRelevanceRate(int relevanceRate) {
		this.relevanceRate = relevanceRate;
	}
	public int getIfviewed() {
		return ifviewed;
	}
	public void setIfviewed(int ifviewed) {
		this.ifviewed = ifviewed;
	}
	public int getJudgement() {
		return judgement;
	}
	public void setJudgement(int judgement) {
		this.judgement = judgement;
	}

	public String getEntryurl() {
		return entryurl;
	}

	public void setEntryurl(String entryurl) {
		this.entryurl = entryurl;
	}
	
}
