import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Score {
	public final int ITEM_NUM = 22; 			// アイテムの種類
	private int stage;
	private int rank;
	private String name;
	private int score;
	private int[] item = new int[ITEM_NUM];
	private final int RANK_MAX = 10;			// 10位まで表示
	
	// コンストラクタ(文字列)
	public Score(String s) {
		// 文字列を分割
		String[] token = s.split("/");
		
		/* 分割した文字列の確認用
		for(String s: token)
			System.out.println(s);
		*/
		
		// 各フィールドに代入
		this.stage = Integer.parseInt(token[0]);
		this.rank = Integer.parseInt(token[1]);
		this.name = token[2];
		this.score = Integer.parseInt(token[3]);
		String[] token2 = token[4].split("\\.");	// エスケープする必要あり
		for (int i = 0; i < token2.length; i++) {
			this.item[i] = Integer.parseInt(token2[i]);
		}
	}
	
	// ステージを取得
	public int getStage() {
		return stage;
	}
	
	public int getScore() {
		return score;
	}
	public String getName(){
		return name;
	}
	
	public int[] getItem(){
		return item;
	}
	
	// 順位を設定
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	// プレイヤ情報を文字列化
	public String toString() {
		String item_s = "";	// アイテム情報
		for (int i = 0; i < ITEM_NUM; i++){
			item_s = item_s + String.format("%02d", item[i]);
			if (i < ITEM_NUM - 1)
				item_s = item_s + ".";
		}
		return "" + stage + "/" + rank + "/" + name + "/" + score + "/" + item_s;
	}
	
	// ソート関数
	public static void sort(ArrayList<Score> score_array) {
		 Collections.sort(score_array, new ScoreComparator());
	}
	
	// 比較関数(異なるステージの時0、s1のスコアが低いとき-1、高いとき1、同じとき0)
	public static int compare(Score s1, Score s2) {
		if (s1.stage != s2.stage) {
			return 0;
		}
		else {
			if (s1.score - s2.score < 0)
				return -1;
			else if (s1.score - s2.score > 0)
				return 1;
			else
				return 0;
		}
	}
	
	// ソート用内部クラス
	static class ScoreComparator implements Comparator<Score> {
		public int compare(Score s1, Score s2) {
			if(s1.stage == s2.stage) {
				return s1.rank < s2.rank ? -1 : 1;
			}
			else {
				return s1.stage < s2.stage ? -1 : 1;
			}
		}
	}
}

