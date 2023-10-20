package dataset.models;

import dataset.enums.MatchResult;

public class FootballMatch {
	public String homeTeam;
	public String awayTeam;
	public Integer fullTimeHomeGoals;
	public Integer fullTimeAwayGoals;
	public MatchResult fullTimeResult;
	public Integer halfTimeHomeGoals;
	public Integer halfTimeAwayGoals;
	public MatchResult halfTimeResult;
	public Integer homeShots;
	public Integer awayShots;
	public Integer homeShotsOnTarget;
	public Integer awayShotsOnTarget;
	public Integer homeCorners;
	public Integer awayCorners;
	public Integer homeFoulsCommitted;
	public Integer awayFoulsCommitted;
	public Integer homeYellowCards;
	public Integer awayYellowCards;
	public Integer homeRedCards;
	public Integer awayRedCards;

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Integer getFullTimeHomeGoals() {
		return fullTimeHomeGoals;
	}

	public void setFullTimeHomeGoals(Integer fullTimeHomeGoals) {
		this.fullTimeHomeGoals = fullTimeHomeGoals;
	}

	public Integer getFullTimeAwayGoals() {
		return fullTimeAwayGoals;
	}

	public void setFullTimeAwayGoals(Integer fullTimeAwayGoals) {
		this.fullTimeAwayGoals = fullTimeAwayGoals;
	}

	public MatchResult getFullTimeResult() {
		return fullTimeResult;
	}

	public void setFullTimeResult(MatchResult fullTimeResult) {
		this.fullTimeResult = fullTimeResult;
	}

	public Integer getHalfTimeHomeGoals() {
		return halfTimeHomeGoals;
	}

	public void setHalfTimeHomeGoals(Integer halfTimeHomeGoals) {
		this.halfTimeHomeGoals = halfTimeHomeGoals;
	}

	public Integer getHalfTimeAwayGoals() {
		return halfTimeAwayGoals;
	}

	public void setHalfTimeAwayGoals(Integer halfTimeAwayGoals) {
		this.halfTimeAwayGoals = halfTimeAwayGoals;
	}

	public MatchResult getHalfTimeResult() {
		return halfTimeResult;
	}

	public void setHalfTimeResult(MatchResult halfTimeResult) {
		this.halfTimeResult = halfTimeResult;
	}

	public Integer getHomeShots() {
		return homeShots;
	}

	public void setHomeShots(Integer homeShots) {
		this.homeShots = homeShots;
	}

	public Integer getAwayShots() {
		return awayShots;
	}

	public void setAwayShots(Integer awayShots) {
		this.awayShots = awayShots;
	}

	public Integer getHomeShotsOnTarget() {
		return homeShotsOnTarget;
	}

	public void setHomeShotsOnTarget(Integer homeShotsOnTarget) {
		this.homeShotsOnTarget = homeShotsOnTarget;
	}

	public Integer getAwayShotsOnTarget() {
		return awayShotsOnTarget;
	}

	public void setAwayShotsOnTarget(Integer awayShotsOnTarget) {
		this.awayShotsOnTarget = awayShotsOnTarget;
	}

	public Integer getHomeCorners() {
		return homeCorners;
	}

	public void setHomeCorners(Integer homeCorners) {
		this.homeCorners = homeCorners;
	}

	public Integer getAwayCorners() {
		return awayCorners;
	}

	public void setAwayCorners(Integer awayCorners) {
		this.awayCorners = awayCorners;
	}

	public Integer getHomeFoulsCommitted() {
		return homeFoulsCommitted;
	}

	public void setHomeFoulsCommitted(Integer homeFoulsCommitted) {
		this.homeFoulsCommitted = homeFoulsCommitted;
	}

	public Integer getAwayFoulsCommitted() {
		return awayFoulsCommitted;
	}

	public void setAwayFoulsCommitted(Integer awayFoulsCommitted) {
		this.awayFoulsCommitted = awayFoulsCommitted;
	}

	public Integer getHomeYellowCards() {
		return homeYellowCards;
	}

	public void setHomeYellowCards(Integer homeYellowCards) {
		this.homeYellowCards = homeYellowCards;
	}

	public Integer getAwayYellowCards() {
		return awayYellowCards;
	}

	public void setAwayYellowCards(Integer awayYellowCards) {
		this.awayYellowCards = awayYellowCards;
	}

	public Integer getHomeRedCards() {
		return homeRedCards;
	}

	public void setHomeRedCards(Integer homeRedCards) {
		this.homeRedCards = homeRedCards;
	}

	public Integer getAwayRedCards() {
		return awayRedCards;
	}

	public void setAwayRedCards(Integer awayRedCards) {
		this.awayRedCards = awayRedCards;
	}

	public static FootballMatch convertDaoToMatch(FootballMatchDao dao) {
		FootballMatch match = new FootballMatch();
		match.homeTeam = dao.HomeTeam;
		match.awayTeam = dao.AwayTeam;
		match.fullTimeHomeGoals = dao.FTHG;
		match.fullTimeAwayGoals = dao.FTAG;
		match.fullTimeResult = MatchResult.fromChar(dao.FTR);
		match.halfTimeHomeGoals = dao.HTHG;
		match.halfTimeAwayGoals = dao.HTAG;
		match.halfTimeResult = MatchResult.fromChar(dao.HTR);
		match.homeShots = dao.HS;
		match.awayShots = dao.AS;
		match.homeShotsOnTarget = dao.HST;
		match.awayShotsOnTarget = dao.AST;
		match.homeCorners = dao.HC;
		match.awayCorners = dao.AC;
		match.homeFoulsCommitted = dao.HF;
		match.awayFoulsCommitted = dao.AF;
		match.homeYellowCards = dao.HY;
		match.awayYellowCards = dao.AY;
		match.homeRedCards = dao.HR;
		match.awayRedCards = dao.AR;

		return match;

	}

}