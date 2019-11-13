public class Player{
	public final int ITEM_NUM = 22; 			// アイテムの種類
	private int[] item = new int[ITEM_NUM];		// 所持アイテム数
	private int id = -1;						// ID（デフォルトは-1）
	private String name = "";					// プレイヤ名（'/'を含まないようにする）
	private int money = 0;						// 所持金

	// コンストラクタ(数値)
	public Player(String name, int id, int cost){
		this.name = name;
		this.id = id;
		this.money = cost;
	}
	// コンストラクタ(文字列)
	public Player(String player) {
		// 文字列を分割
		String[] token = player.split("/");

		/* 分割した文字列の確認用
		for(String s: token)
			System.out.println(s);
		*/

		// 各フィールドに代入
		this.id = Integer.parseInt(token[0]);
		String[] token2 = token[1].split("\\.");	// エスケープする必要あり
		for (int i = 0; i < token2.length; i++) {
			this.item[i] = Integer.parseInt(token2[i]);
		}
		this.name = token[2];
		this.money = Integer.parseInt(token[3]);
	}

	// ゲッタ
	public int getItem(int i) {return item[i];}
	public int getId() {return id;};
	public String getName() {return name;};
	public int getMoney() {return money;};
	// セッタ
	public void setItem(int[] item) {
		for(int i = 0; i < ITEM_NUM; i++) {
			this.item[i] = item[i];
		}
	}
	public void setMoney(int a){
		this.money=a;
	}

	// プレイヤ情報を文字列化
	public String toString() {
		String item_s = "";	// アイテム情報
		for (int i = 0; i < ITEM_NUM; i++){
			item_s = item_s + String.format("%02d", item[i]);
			if (i < ITEM_NUM - 1)
				item_s = item_s + ".";
		}
		return "" + id + "/" + item_s + "/" + name + "/" + money;
	}
}

