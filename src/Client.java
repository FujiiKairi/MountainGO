import java.applet.AudioClip;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener{
	Player player;


	//現在描画している画面の番号
	//0:タイトル、1：ログイン、新規登録画面、2:新規登録画面、3:メニュー、4:アカウント管理画面、5:ショップ
	//6:富士ステージ詳細、7:エベレストステージ詳細、8:マシフステージ詳細
	//9:ゲーム画面、10:ゲームオーバー画面、11:アイコン変更画面
	//12:アイテム、13:ルール選択,14スコア表示)

	int nowCanvasNumber;
	private ArrayList<Score> score_array;
	int numOfMountain;//0富士山、１エベレスト、２マシフ
	int overNumber;//ゲームオーバーの理由
	int time=-1;//何時間立ったか
	int yamabikoNum=0;//何回進めるか
	String overLog;//ゲームオーバーのログ
	//ソケット
	Socket socket;
	//ポート番号
	int port=4003;
	//サーバのアドレス
	String serverAddress="133.34.237.57";//score_array.get(i).getStage();

	//文字列読み取り
	BufferedReader reader;
	//文字列書き込み
	PrintWriter writer;
	//オブジェクト読み取り
	ObjectInputStream objReader;
	//オブジェクト書き込み
	ObjectOutputStream objWriter;

	Mountain mountain;
	AudioClip clip;;

	//汎用ボタン
	JButton menuButton1;
	JButton menuButton2;
	JButton menuButton3;
	JButton menuButton4;//メニューへ
	JButton menuButton5;
	JButton menuButton6;
	JButton ruleButton;//ルール説明ボタン
	JButton ruleButton1;//ルール説明ボタン
	JButton[] itemSelectButton;//アイテム選択ボタン
	JButton newAccountButton1;//新規登録ボタン
	JButton newAccountButton2;
	//タイトル
	JButton climbButton;//登頂開始のボタン
	JButton endButton;//終了ボタン
	JButton endButton1;
	//新規登録、ログイン画面
	JTextField playerName1;
	JPasswordField password1;
	JTextField playerName2;
	JPasswordField password2;
	JButton roginButton;//ログイン
	//メニュー画面
	JButton fuziSelectButton;
	JButton evSelectButton;
	JButton maSelectButton;//山の選択
	JButton accountManagementButton;//アカウント管理ボタン
	JButton shopButton;//ショップボタン
	JButton titleButton;//タイトルへ戻る
	//アカウント管理画面
	JButton changeIconButton;//アイコン変更ボタン
	JButton maintainChangeButton;//変更保存
	JButton resetButton;//リセットボタン
	JButton pitem1Button;
	JButton pitem2Button;
	JButton pitem3Button;
	JButton pitem4Button;
	JButton pitem5Button;
	JButton pitem6Button;
	JButton pitem7Button;
	JButton pitem8Button;
	JButton pitem9Button;
	JButton pitem10Button;
	JButton pitem11Button;
	JButton pitem12Button;
	JButton pitem13Button;
	JButton pitem14Button;
	JButton pitem15Button;
	JButton pitem16Button;
	JButton pitem17Button;
	JButton pitem18Button;
	JButton pitem19Button;
	JButton pitem20Button;
	JButton pitem21Button;

	JButton mitem1Button;
	JButton mitem2Button;
	JButton mitem3Button;
	JButton mitem4Button;
	JButton mitem5Button;
	JButton mitem6Button;
	JButton mitem7Button;
	JButton mitem8Button;
	JButton mitem9Button;
	JButton mitem10Button;
	JButton mitem11Button;
	JButton mitem12Button;
	JButton mitem13Button;
	JButton mitem14Button;
	JButton mitem15Button;
	JButton mitem16Button;
	JButton mitem17Button;
	JButton mitem18Button;
	JButton mitem19Button;
	JButton mitem20Button;
	JButton mitem21Button;
	JButton backButton;//前へ
	JButton frontButton;//後ろへ
	//アイコン画面
	JButton icon1Button;
	JButton icon2Button;
	JButton icon3Button;
	JButton icon4Button;
	JButton icon5Button;
	JButton icon6Button;
	JButton icon7Button;
	JButton icon8Button;
	JButton icon9Button;
	JButton icon10Button;
	JButton changeButton;
	JButton backManagementButton;
	//ショップ
	JButton nextButton;
	JButton shop1Button;
	JButton shop2Button;
	JButton shop3Button;
	JButton shop4Button;
	JButton shop5Button;
	JButton shop6Button;
	JButton shop7Button;
	//ステージ選択
	JButton startButton1;
	JButton startButton2;
	JButton startButton3;//ゲームの開始ボタン
	//ゲーム画面
	JButton treeButton;//木を揺らす
	JButton itemButton;//アイテムメニューの表示
	JButton yamabikoButton;//やまびこボタン
	JButton restButton;//休憩ボタン
	JButton roadSelectButton1;//どの道を行くか
	JButton roadSelectButton2;
	JButton roadSelectButton3;
	JButton roadSelectButton4;
	JButton roadSelectButton5;
	JButton roadSelectButton6;
	JButton boardButton[][];//盤面のボタン
	//ゲームオーバー画面
	JButton okButton;
	//アイテム選択画面
	JButton useItemButton;//アイテムを使う
	JButton backBoardButton;//盤面に戻る
	//ルール説明
	JButton backToButton;

	//描画用コンテナ
	Container container;
	CardLayout layout;

	//描画用パネル
	TitlePanel titlePanel;
	RoginPanel roginPanel;
	NaccountPanel naccountPanel;
	MenuPanel menuPanel;
	ManagementPanel managementPanel;
	IconPanel iconPanel;
	ShopPanel shopPanel;
	FStagePanel fstagePanel;
	EStagePanel estagePanel;
	MStagePanel mstagePanel;
	GamePanel gamePanel;
	GameOverPanel gameOverPanel;
	ItemPanel itemPanel;
	RulePanel rulePanel;
	ScorePanel scorePanel;

	//アイテム生成
	Item drink;
	Item sleepingBag;
	Item climbShoes;
	Item cap;
	Item aizen;
	Item reinCoat;
	Item rantan;
	Item headLump;
	Item kairo;
	Item misoSoup;
	Item onigiri;
	Item miharukasu;
	Item bell;
	Item medicine;
	Item againstColdWear;
	Item airBag;
	Item omamori1;
	Item omamori2;
	Item coffeeMaker;
	Item waterBottole;
	Item Oxygentank;
	//キャンバスの大きさ

	//画像
	ImageIcon bgTitle;
	ImageIcon bgRogin;
	ImageIcon bgA;
	ImageIcon mtFuzi;
	ImageIcon mtMa;
	ImageIcon mtEv;
	ImageIcon bgStageRank;
	ImageIcon bgText;
	ImageIcon bgText2;
	ImageIcon bgShop;
	ImageIcon gameFuji;
	ImageIcon gameEv;
	ImageIcon gameMa;
	ImageIcon gameNightFuji;
	ImageIcon gameNightEv;
	ImageIcon gameNightMa;
	ImageIcon gameUnder;
	ImageIcon goal;
	ImageIcon goalLine;
	ImageIcon rest;
	ImageIcon team;
	ImageIcon nothing;
	ImageIcon detailFuji;
	ImageIcon detailEv;
	ImageIcon detailMa;
	ImageIcon managementWood;
	ImageIcon icon1;
	ImageIcon icon2;
	ImageIcon icon3;
	ImageIcon icon4;
	ImageIcon icon5;
	ImageIcon icon6;
	ImageIcon icon7;
	ImageIcon icon8;
	ImageIcon icon9;
	ImageIcon icon10;
	ImageIcon miniIcon1;
	ImageIcon miniIcon2;
	ImageIcon miniIcon3;
	ImageIcon miniIcon4;
	ImageIcon miniIcon5;
	ImageIcon miniIcon6;
	ImageIcon miniIcon7;
	ImageIcon miniIcon8;
	ImageIcon miniIcon9;
	ImageIcon miniIcon10;

	ImageIcon tsuzu;
	ImageIcon tsuitou;
	ImageIcon tsanso;
	ImageIcon trantan;
	ImageIcon trainwear;
	ImageIcon tonigiri;
	ImageIcon tomamori2;
	ImageIcon tomamori1;
	ImageIcon tnebukuro;//scor_array<>.get(i).getStage();
	ImageIcon tmiso;
	ImageIcon tmedicine;
	ImageIcon tlump;
	ImageIcon tkutu;
	ImageIcon tkairo;
	ImageIcon tdrink;
	ImageIcon tcoffee;
	ImageIcon tcd;
	ImageIcon tcap;
	ImageIcon tairbag;
	ImageIcon tboukan;
	ImageIcon taizen;
	Client(String title){
		//画像
		bgTitle=new ImageIcon("bgTitle.jpg");
		bgRogin=new ImageIcon("bgRogin.jpg");
		bgA=new ImageIcon("bgA.jpg");
		mtFuzi=new ImageIcon("mtFuzi.jpg");
		mtMa=new ImageIcon("mtMa.jpg");
		mtEv=new ImageIcon("mtEv.jpg");
		bgStageRank=new ImageIcon("bgStageRank.jpg");
		bgText=new ImageIcon("bgText.jpg");
		bgText2=new ImageIcon("bgText2.jpg");
		bgShop=new ImageIcon("bgShop.jpg");
		detailFuji=new ImageIcon("detailFuji.jpg");
		detailEv=new ImageIcon("detailEv.jpg");
		detailMa=new ImageIcon("detailMa.jpg");
		gameFuji=new ImageIcon("gameFuji.jpg");
		gameEv=new ImageIcon("gameEv.jpg");
		gameMa=new ImageIcon("gameMa.jpg");
		gameNightFuji=new ImageIcon("gameNightFuji.jpg");
		gameNightEv=new ImageIcon("gameNightEv.jpg");
		gameNightMa=new ImageIcon("gameNightMa.jpg");
		gameUnder=new ImageIcon("gameUnder.jpg");
		goal=new ImageIcon("goal.jpg");
		rest =new ImageIcon("rest.jpg");
		goalLine=new ImageIcon("goalLine.jpg");
		team=new ImageIcon("team.jpg");
		nothing=new ImageIcon("nothing.jpg");
		miniIcon1=new ImageIcon("miniIcon1.jpg");
		miniIcon2=new ImageIcon("miniIcon2.jpg");
		miniIcon3=new ImageIcon("miniIcon3.jpg");
		miniIcon4=new ImageIcon("miniIcon4.jpg");
		miniIcon5=new ImageIcon("miniIcon5.jpg");
		miniIcon6=new ImageIcon("miniIcon6.jpg");
		miniIcon7=new ImageIcon("miniIcon7.jpg");
		miniIcon8=new ImageIcon("miniIcon8.jpg");
		miniIcon9=new ImageIcon("miniIcon9.jpg");
		miniIcon10=new ImageIcon("miniIco101.jpg");
		icon1=new ImageIcon("icon1.jpg");
		icon2=new ImageIcon("icon2.jpg");
		icon3=new ImageIcon("icon3.jpg");
		icon4=new ImageIcon("icon4.jpg");
		icon5=new ImageIcon("icon5.jpg");
		icon6=new ImageIcon("icon6.jpg");
		icon7=new ImageIcon("icon7.jpg");
		icon8=new ImageIcon("icon8.jpg");
		icon9=new ImageIcon("icon9.jpg");
		icon10=new ImageIcon("icon10.jpg");
		managementWood=new ImageIcon("managementWood.jpg");
		tsuzu=new ImageIcon("suzu.jpg");
		tsuitou=new ImageIcon("suitou.jpg");
		tsanso=new ImageIcon("sanso.jpg");
		trantan=new ImageIcon("rantan.jpg");
		trainwear=new ImageIcon("rainwear.jpg");
		tonigiri=new ImageIcon("onigiri.jpg");
		tomamori2=new ImageIcon("omamori2.jpg");
		tomamori1=new ImageIcon("omamori1.jpg");
		tnebukuro=new ImageIcon("nebukuro.jpg");
		tmiso=new ImageIcon("miso.jpg");
		tmedicine=new ImageIcon("medicine.jpg");
		tlump=new ImageIcon("lump.jpg");
		tkutu=new ImageIcon("kutu.jpg");
		tkairo=new ImageIcon("kairo.jpg");
		tdrink=new ImageIcon("drink.jpg");
		tcoffee=new ImageIcon("coffee.jpg");
		tcd=new ImageIcon("cd.jpg");
		tcap=new ImageIcon("cap.jpg");
		tairbag=new ImageIcon("airbag.jpg");
		tboukan=new ImageIcon("boukan.jpg");
		taizen=new ImageIcon("aizen.jpg");
		//曲

		//arrayList
		score_array=new ArrayList<Score>();

		//ボタン
		menuButton1=new JButton("メニュー");
		menuButton2=new JButton("メニュー");
		menuButton3=new JButton("メニュー");
		menuButton4=new JButton("メニュー");
		menuButton5=new JButton("メニュー");
		menuButton6=new JButton("メニュー");
		ruleButton=new JButton("ルール説明");
		ruleButton1=new JButton("ルール説明");
		itemSelectButton=new JButton[30];
		climbButton=new JButton("登山開始");
		endButton=new JButton("終了");
		endButton1=new JButton("終了");
		newAccountButton1=new JButton("新規登録");
		nextButton=new JButton("次のページ");
		newAccountButton2=new JButton("登録する");
		roginButton=new JButton("ログイン");
		fuziSelectButton=new JButton("f");
		evSelectButton=new JButton("e");
		maSelectButton=new JButton("m");
		accountManagementButton=new JButton("アカウント管理");
		icon1Button=new JButton("1");
		icon2Button=new JButton("2");
		icon3Button=new JButton("3");
		icon4Button=new JButton("4");
		icon5Button=new JButton("5");
		icon6Button=new JButton("6");
		icon7Button=new JButton("7");
		icon8Button=new JButton("8");
		icon9Button=new JButton("9");
		icon10Button=new JButton("10");
		changeButton=new JButton("変更");

		pitem1Button=new JButton("p1");
		pitem2Button=new JButton("p2");
		pitem3Button=new JButton("p3");
		pitem4Button=new JButton("p4");
		pitem5Button=new JButton("p5");
		pitem6Button=new JButton("p6");
		pitem7Button=new JButton("p7");
		pitem8Button=new JButton("p8");
		pitem9Button=new JButton("p9");
		pitem10Button=new JButton("p10");
		pitem11Button=new JButton("p11");
		pitem12Button=new JButton("p12");
		pitem13Button=new JButton("p13");
		pitem14Button=new JButton("p14");
	 	pitem15Button=new JButton("p15");
		pitem16Button=new JButton("p16");
		pitem17Button=new JButton("p17");
		pitem18Button=new JButton("p18");
		pitem19Button=new JButton("p19");
		pitem20Button=new JButton("p20");
		pitem21Button=new JButton("p21");

		mitem1Button=new JButton("m1");
		mitem2Button=new JButton("m2");
		mitem3Button=new JButton("m3");
		mitem4Button=new JButton("m4");
		mitem5Button=new JButton("m5");
		mitem6Button=new JButton("m6");
		mitem7Button=new JButton("m7");
		mitem8Button=new JButton("m8");
		mitem9Button=new JButton("m9");
		mitem10Button=new JButton("m10");
		mitem11Button=new JButton("m11");
		mitem12Button=new JButton("m12");
		mitem13Button=new JButton("m13");
		mitem14Button=new JButton("m14");
		mitem15Button=new JButton("m15");
		mitem16Button=new JButton("m16");
		mitem17Button=new JButton("m17");
		mitem18Button=new JButton("m18");
		mitem19Button=new JButton("m19");
		mitem20Button=new JButton("m20");
		mitem21Button=new JButton("m21");
		backManagementButton=new JButton("戻る");
		shopButton=new JButton("ショップ");
		shop1Button=new JButton("1");
		shop2Button=new JButton("2");
		shop3Button=new JButton("3");
		shop4Button=new JButton("4");
		shop5Button=new JButton("5");
		shop6Button=new JButton("6");
		shop7Button=new JButton("7");
		titleButton=new JButton("タイトル");
		changeIconButton=new JButton("アイコン変更");
		resetButton=new JButton("リセット");
		maintainChangeButton=new JButton("変更保存");
		backButton=new JButton("前へ");
		frontButton=new JButton("後へ");
		startButton1=new JButton("山へ向かう");
		startButton2=new JButton("山へ向かう");
		startButton3=new JButton("山へ向かう");
		treeButton=new JButton("木を揺らす");
		itemButton=new JButton("アイテム");
		yamabikoButton=new JButton("やまびこ");
		restButton=new JButton("休憩する");
		useItemButton=new JButton("アイテムを使う");
		okButton=new JButton("OK");
		roadSelectButton1=new JButton("");
		roadSelectButton2=new JButton("");
		roadSelectButton3=new JButton("");
		roadSelectButton4=new JButton("");
		roadSelectButton5=new JButton("");
		roadSelectButton6=new JButton("");
		backBoardButton=new JButton("");
		backToButton=new JButton("戻る");
		boardButton=new JButton[11][13];
		//リスナーに追加
		ruleButton.addActionListener(this);
		ruleButton1.addActionListener(this);
		climbButton.addActionListener(this);
		endButton.addActionListener(this);
		endButton1.addActionListener(this);
		newAccountButton1.addActionListener(this);
		newAccountButton2.addActionListener(this);
		roginButton.addActionListener(this);
		accountManagementButton.addActionListener(this);
		fuziSelectButton.addActionListener(this);
		evSelectButton.addActionListener(this);
		maSelectButton.addActionListener(this);
		shopButton.addActionListener(this);
		shop1Button.addActionListener(this);
		shop2Button.addActionListener(this);
		shop3Button.addActionListener(this);
		shop4Button.addActionListener(this);
		shop5Button.addActionListener(this);
		shop6Button.addActionListener(this);
		shop7Button.addActionListener(this);
		nextButton.addActionListener(this);
		titleButton.addActionListener(this);
		changeIconButton.addActionListener(this);
		icon1Button.addActionListener(this);
		icon2Button.addActionListener(this);
		icon3Button.addActionListener(this);
		icon4Button.addActionListener(this);
		icon5Button.addActionListener(this);
		icon6Button.addActionListener(this);
		icon7Button.addActionListener(this);
		icon8Button.addActionListener(this);
		icon9Button.addActionListener(this);
		icon10Button.addActionListener(this);
		changeButton.addActionListener(this);
		pitem1Button.addActionListener(this);
		pitem2Button.addActionListener(this);
		pitem3Button.addActionListener(this);
		pitem4Button.addActionListener(this);
		pitem5Button.addActionListener(this);
		pitem6Button.addActionListener(this);
		pitem7Button.addActionListener(this);
		pitem8Button.addActionListener(this);
		pitem9Button.addActionListener(this);
		pitem10Button.addActionListener(this);
		pitem11Button.addActionListener(this);
		pitem12Button.addActionListener(this);
		pitem13Button.addActionListener(this);
		pitem14Button.addActionListener(this);
		pitem15Button.addActionListener(this);
		pitem16Button.addActionListener(this);
		pitem17Button.addActionListener(this);
		pitem18Button.addActionListener(this);
		pitem19Button.addActionListener(this);
		pitem20Button.addActionListener(this);
		pitem21Button.addActionListener(this);
	 	mitem1Button.addActionListener(this);
	 	mitem2Button.addActionListener(this);
		mitem3Button.addActionListener(this);
		mitem4Button.addActionListener(this);
		mitem5Button.addActionListener(this);
		mitem6Button.addActionListener(this);
		mitem7Button.addActionListener(this);
		mitem8Button.addActionListener(this);
		mitem9Button.addActionListener(this);
		mitem10Button.addActionListener(this);
		mitem11Button.addActionListener(this);
		mitem12Button.addActionListener(this);
		mitem13Button.addActionListener(this);
		mitem14Button.addActionListener(this);
		mitem15Button.addActionListener(this);
		mitem16Button.addActionListener(this);
		mitem17Button.addActionListener(this);
		mitem18Button.addActionListener(this);
		mitem19Button.addActionListener(this);
		mitem20Button.addActionListener(this);
		mitem21Button.addActionListener(this);
		resetButton.addActionListener(this);
		backManagementButton.addActionListener(this);
		maintainChangeButton.addActionListener(this);
		backButton.addActionListener(this);
		menuButton1.addActionListener(this);
		menuButton2.addActionListener(this);
		menuButton3.addActionListener(this);
		menuButton4.addActionListener(this);
		menuButton5.addActionListener(this);
		menuButton6.addActionListener(this);
		startButton1.addActionListener(this);
		startButton2.addActionListener(this);
		startButton3.addActionListener(this);
		treeButton.addActionListener(this);
		itemButton.addActionListener(this);
		yamabikoButton.addActionListener(this);
		restButton.addActionListener(this);
		roadSelectButton1.addActionListener(this);
		roadSelectButton2.addActionListener(this);
		roadSelectButton3.addActionListener(this);
		roadSelectButton4.addActionListener(this);
		roadSelectButton5.addActionListener(this);
		roadSelectButton6.addActionListener(this);
	    useItemButton.addActionListener(this);
	    okButton.addActionListener(this);
	    backBoardButton.addActionListener(this);
	    backToButton.addActionListener(this);
	    //テキストフィールド
	    playerName1 = new JTextField("", 10);
	    password1= new JPasswordField(20);
	    playerName2 = new JTextField("", 10);
	    password2= new JPasswordField(20);
	    //フレーム作成
	    setTitle(title);
	    setBounds(250, 30, 1000, 700);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    titlePanel=new TitlePanel();
		roginPanel=new RoginPanel();
		naccountPanel=new NaccountPanel();
		menuPanel=new MenuPanel();
		managementPanel=new ManagementPanel() ;
		iconPanel=new IconPanel();
		shopPanel=new ShopPanel();
		fstagePanel=new FStagePanel();
		estagePanel=new EStagePanel();
		mstagePanel=new MStagePanel();
		gameOverPanel=new GameOverPanel();
		itemPanel=new ItemPanel();
		rulePanel=new RulePanel();



		//アイテム作成
		bell=new Item("鈴",1,2,100);
		waterBottole=new Item("水筒",2,2,500);
		Oxygentank=new Item("酸素ボンベ",3,3,2700);
		rantan=new Item("ランタン",4,2,6000);
		reinCoat=new Item("レインウェア",5,1,2000);
		onigiri=new Item("おかん特製おにぎり",6,3,350);
		omamori2=new Item("豪運のお守り",7,2,300);
		omamori1=new Item("金運のお守り",8,1,100);
		sleepingBag=new Item("高級寝袋",9,3,12000);
		misoSoup=new Item("おかん特製味噌汁",10,3,350);
		medicine=new Item("高山病の薬",11,1,6000);
		headLump=new Item("ヘッドランプ",12,2,5000);
		climbShoes=new Item("高級登山靴",13,2,9000);
		kairo=new Item("ホッカイロ",14,2,500);
		drink=new Item("栄養ドリンク",15,4,1000);
		coffeeMaker=new Item("コーヒーメーカー",16,0,1500);
		miharukasu=new Item("みはるかすのCD",17,0,100);
		cap=new Item("帽子",18,1,2000);
		airBag=new Item("火薬式エアバッグ",19,2,15000);
		againstColdWear=new Item("防寒具",20,2,4000);
		aizen=new Item("アイゼン",21,2,6500);





		container=getContentPane();
		layout=new CardLayout();
		container.setLayout(layout);
		container.add(titlePanel,"title");
		container.add(roginPanel,"rogin");
		container.add(naccountPanel,"naccount");
		container.add(menuPanel,"menu");
		container.add(managementPanel,"management");
		container.add(iconPanel,"icon");
		container.add(shopPanel,"shop");


		container.add(gameOverPanel,"over");
		container.add(itemPanel,"item");
		container.add(rulePanel,"rule");


		//スタート画面の表示
		nowCanvasNumber=0;
		connectToServer();
		paintTitleCanvas();



	 }//コンストラクタの終わり

	 public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Client frame = new Client("Mountain Go");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	 /*//プレイヤ名の取得
	 public String getPlayerName(){
		 return playerName;
	 }

	 //idの取得
	 public int getID(){
		 return id;
	 }*/

	 //サーバーへ接続
	 public void connectToServer(){
		try{
			//socket=new Socket(serverAddress,port);
			socket=new Socket("localhost",port);
			//socket.connect(socketAddress);

			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer=new PrintWriter(socket.getOutputStream(),true);
			writer.flush();
			//接続成功
			if(socket!=null){
				//クライアント情報の送信
				System.out.println("接続成功");
				clientEntry();



				//失敗
				}else{
					//ソケットを閉じる
				if(socket!=null){
					socket.close();
					System.exit(1);
				}

				//接続切れ画面へ
				System.out.println("接続切れ");
				nowCanvasNumber=3;
				//paintConnectionFailedCanvas();
			}
		}catch(SocketException e){
			System.out.println("接続失敗"+e);
			//nowCanvasNumber=3;
			//paintConnectionFailedCanvas();
		}
		catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println("接続失敗");
				//nowCanvasNumber=3;
				//paintConnectionFailedCanvas();
		}
	}

	//自分のプレイヤ（クライアント識別）情報の送信
	public void clientEntry(){
		try{
			objWriter=new ObjectOutputStream(socket.getOutputStream());
			objWriter.writeObject(player);
		}catch(IOException e){
		}
	}

	//メッセージの受信()
	public String getMessage(){
		String line=null;
		try{

				line=reader.readLine();
		}catch(IOException e){
		}
		return line;
	}
	//自分のプレイヤ情報の受信
	public void getPlayer(){

			System.out.println("1");

			String a=getMessage();
			System.out.println("aは"+a);
			player=new Player(a);
			player.setMoney(10000);

	}

		//サーバにメッセージを送信(操作情報：String)
	public void sendMessage(String message){


				writer.flush();
				System.out.println("messageは"+message);
				writer.println(message);
				writer.flush();
	}

	public void actionPerformed(ActionEvent e){

		//押されたボタンのテキストを保持
		String command=null;
		command=e.getActionCommand();
		System.out.println("[command]"+command);



		//START画面で
		if(nowCanvasNumber==0){//タイトル画面
			if(command.equals(climbButton.getText())){
				nowCanvasNumber=1;
				//player=new Player("kairi",1,10000);
				paintRoginCanvas();
			}
			if(command.equals(endButton.getText())){
				try {
					writer.close();
					reader.close();
				} catch (IOException e1) {

				}
				try {
					socket.close();
					System.out.println("ソケットを閉じました");
				} catch (IOException e1) {
				}
				System.exit(0);
			}
		}
		if(nowCanvasNumber==1){//ログイン新規登録
			if(command.equals(newAccountButton1.getText())){
				nowCanvasNumber=2;

				paintNaccountCanvas();
			}
			if(command.equals(roginButton.getText())){
				//プレイヤオブジェクト生成
				sendMessage("no");
				sendMessage(roginPanel.getName());
				//String st= String.valueOf(password.getPassword());
				sendMessage(roginPanel.getPass());
				System.out.println("メッセ送った");
				String m=getMessage();
				System.out.println("mは"+m);
				if(getMessage()=="retry"){
					System.out.println("retry");
				}
				else{
					//サーバーからプレイヤを受け取る
					System.out.println(socket);
					getPlayer();
					nowCanvasNumber=3;
					paintMenuCanvas();
				}
			}
		}
		if(nowCanvasNumber==2){//新規登録
			if(command.equals(newAccountButton2.getText())){
				//プレイヤオブジェクト生成
				//プレイヤネームの入力が無ければ再入力
				//naccountPanel.getName()==""||naccountPanel.getPass()==""
				if(naccountPanel.getName()==""||naccountPanel.getPass()==""){

					naccountPanel=new NaccountPanel();
					container.add(naccountPanel,"naccount");
					paintNaccountCanvas();
					naccountPanel.reEnter();
				}
				else{

					sendMessage("yes");
					sendMessage(naccountPanel.getName());

					//String st= String.valueOf(password.getPassword());
					sendMessage(naccountPanel.getPass());

					System.out.println("メッセ送った");
					if(getMessage()=="retry"){
						System.out.println("retry");
					}
					else{
						//サーバーからプレイヤを受け取る
						System.out.println(socket);
						getPlayer();
						nowCanvasNumber=3;
						paintMenuCanvas();
					}
				}
			}

		}
		if(nowCanvasNumber==3){//メニュー
			System.out.println("プレイヤ名"+player.getName());
			if(command.equals(accountManagementButton.getText())){
				nowCanvasNumber=4;
				paintManagementCanvas();
				//アイテムとお金の同期
				for(int i=0;i<22;i++){
					managementPanel.setItem(i,player.getItem(i));
				}
				managementPanel.setName(player.getName());
				managementPanel.setMoney(player.getMoney());
				managementPanel.paintHavingNum();
			}
			if(command.equals(shopButton.getText())){
				nowCanvasNumber=5;
				//アイテムとお金の同期

				for(int i=0;i<22;i++){
					shopPanel.setItem(i,player.getItem(i));
				}
				shopPanel.setMoney(player.getMoney());
				paintShopCanvas();
			}
			if(command.equals(titleButton.getText())){
				nowCanvasNumber=0;
				paintTitleCanvas();
			}
			if(command.equals(ruleButton1.getText())){
				nowCanvasNumber=13;

				paintRuleCanvas();
			}
			if(command.equals(fuziSelectButton.getText())){
				nowCanvasNumber=6;
				//スコア情報の受け取り
				sendMessage("getScore");
				String a=getMessage();
				int k=Integer.parseInt(a);
				String s;
				score_array=new ArrayList<Score>();
				for(int i=0;i<k;i++){
					s=getMessage();
					score_array.add(new Score(s));
					}
				fstagePanel=new FStagePanel();
				container.add(fstagePanel,"fstage");
				paintFStageCanvas();
				for(int i=0;i<score_array.size();i++){
					if(score_array.get(i).getStage()==1){

						fstagePanel.paintRank(score_array.get(i).getName(),score_array.get(i).getScore());
					}
				}

			}
			if(command.equals(evSelectButton.getText())){
				nowCanvasNumber=7;
				//スコア情報の受け取り
				sendMessage("getScore");
				String a=getMessage();
				int k=Integer.parseInt(a);
				String s;
				score_array=new ArrayList<Score>();
				for(int i=0;i<k;i++){
					s=getMessage();
					score_array.add(new Score(s));
					}
				estagePanel=new EStagePanel();
				container.add(estagePanel,"estage");
				paintEStageCanvas();
				for(int i=0;i<score_array.size();i++){
					if(score_array.get(i).getStage()==2){

						estagePanel.paintRank(score_array.get(i).getName(),score_array.get(i).getScore());
					}
				}
			}
			if(command.equals(maSelectButton.getText())){
				nowCanvasNumber=8;
				//スコア情報の受け取り
				sendMessage("getScore");
				String a=getMessage();
				int k=Integer.parseInt(a);
				String s;
				score_array=new ArrayList<Score>();
				for(int i=0;i<k;i++){
					s=getMessage();
					score_array.add(new Score(s));
					}
				mstagePanel=new MStagePanel();
				container.add(mstagePanel,"mstage");
				paintMStageCanvas();
				for(int i=0;i<score_array.size();i++){
					if(score_array.get(i).getStage()==3){

						mstagePanel.paintRank(score_array.get(i).getName(),score_array.get(i).getScore());
					}
				}
			}
		}
		if(nowCanvasNumber==4){//アカウント管理画面
			if(command.equals(menuButton4.getText())){
				nowCanvasNumber=3;
				for(int i=0;i<22;i++){
					System.out.println(managementPanel.item[i]);
				}
				paintMenuCanvas();
			}

			if(command.equals(pitem1Button.getText())){
				if(managementPanel.item[1]>=1&&managementPanel.cost+bell.cost<=10){
					managementPanel.paintTake(1);
					managementPanel.cost=managementPanel.cost+bell.cost;
				}
			}
			if(command.equals(pitem2Button.getText())){
				if(managementPanel.item[2]>=1&&managementPanel.cost+waterBottole.cost<=10){
					managementPanel.paintTake(2);
					managementPanel.cost=managementPanel.cost+waterBottole.cost;
				}
			}
			if(command.equals(pitem3Button.getText())){
				if(managementPanel.item[3]>=1&&managementPanel.cost+Oxygentank.cost<=10){
					managementPanel.paintTake(3);
					managementPanel.cost=managementPanel.cost+Oxygentank.cost;
				}
			}
			if(command.equals(pitem4Button.getText())){
				if(managementPanel.item[4]>=1&&managementPanel.cost+rantan.cost<=10){
					managementPanel.paintTake(4);
					managementPanel.cost=managementPanel.cost+rantan.cost;
				}
			}
			if(command.equals(pitem5Button.getText())){
				if(managementPanel.item[5]>=1&&managementPanel.cost+reinCoat.cost<=10){
					managementPanel.paintTake(5);
					managementPanel.cost=managementPanel.cost+reinCoat.cost;
				}
			}
			if(command.equals(pitem6Button.getText())){
				if(managementPanel.item[6]>=1&&managementPanel.cost+onigiri.cost<=10){
					managementPanel.paintTake(6);
					managementPanel.cost=managementPanel.cost+onigiri.cost;
				}
			}
			if(command.equals(pitem7Button.getText())){
				if(managementPanel.item[7]>=1&&managementPanel.cost+omamori2.cost<=10){
					managementPanel.paintTake(7);
					managementPanel.cost=managementPanel.cost+omamori2.cost;
				}
			}
			if(command.equals(pitem8Button.getText())){
				if(managementPanel.item[8]>=1&&managementPanel.cost+omamori1.cost<=10){
					managementPanel.paintTake(8);
					managementPanel.cost=managementPanel.cost+omamori1.cost;
				}
			}
			if(command.equals(pitem9Button.getText())){
				if(managementPanel.item[9]>=1&&managementPanel.cost+sleepingBag.cost<=10){
					managementPanel.paintTake(9);
					managementPanel.cost=managementPanel.cost+sleepingBag.cost;
				}
			}
			if(command.equals(pitem10Button.getText())){
				if(managementPanel.item[10]>=1&&managementPanel.cost+misoSoup.cost<=10){
					managementPanel.paintTake(10);
					managementPanel.cost=managementPanel.cost+misoSoup.cost;
				}
			}
			if(command.equals(pitem11Button.getText())){
				if(managementPanel.item[11]>=1&&managementPanel.cost+medicine.cost<=10){
					managementPanel.paintTake(11);
					managementPanel.cost=managementPanel.cost+medicine.cost;
				}
			}
			if(command.equals(pitem12Button.getText())){
				if(managementPanel.item[12]>=1&&managementPanel.cost+headLump.cost<=10){
					managementPanel.paintTake(12);
					managementPanel.cost=managementPanel.cost+headLump.cost;
				}
			}
			if(command.equals(pitem13Button.getText())){
				if(managementPanel.item[13]>=1&&managementPanel.cost+climbShoes.cost<=10){
					managementPanel.paintTake(13);
					managementPanel.cost=managementPanel.cost+climbShoes.cost;
				}
			}
			if(command.equals(pitem14Button.getText())){
				if(managementPanel.item[14]>=1&&managementPanel.cost+kairo.cost<=10){
					managementPanel.paintTake(14);
					managementPanel.cost=managementPanel.cost+kairo.cost;
				}
			}
			if(command.equals(pitem15Button.getText())){
				if(managementPanel.item[15]>=1&&managementPanel.cost+drink.cost<=10){
					managementPanel.paintTake(15);
					managementPanel.cost=managementPanel.cost+drink.cost;
				}
			}
			if(command.equals(pitem16Button.getText())){
				if(managementPanel.item[16]>=1&&managementPanel.cost+coffeeMaker.cost<=10){
					managementPanel.paintTake(16);
					managementPanel.cost=managementPanel.cost+coffeeMaker.cost;
				}
			}
			if(command.equals(pitem17Button.getText())){
				if(managementPanel.item[17]>=1&&managementPanel.cost+miharukasu.cost<=10){
					managementPanel.paintTake(17);
					managementPanel.cost=managementPanel.cost+miharukasu.cost;
				}
			}
			if(command.equals(pitem18Button.getText())){
				if(managementPanel.item[18]>=1&&managementPanel.cost+cap.cost<=10){
					managementPanel.paintTake(18);
					managementPanel.cost=managementPanel.cost+cap.cost;
				}
			}
			if(command.equals(pitem19Button.getText())){
				if(managementPanel.item[19]>=1&&managementPanel.cost+airBag.cost<=10){
					managementPanel.paintTake(19);
					managementPanel.cost=managementPanel.cost+airBag.cost;
				}
			}
			if(command.equals(pitem20Button.getText())){
				if(managementPanel.item[20]>=1&&managementPanel.cost+againstColdWear.cost<=10){
					managementPanel.paintTake(20);
					managementPanel.cost=managementPanel.cost+againstColdWear.cost;
				}
			}
			if(command.equals(pitem21Button.getText())){
				if(managementPanel.item[21]>=1&&managementPanel.cost+aizen.cost<=10){
					managementPanel.paintTake(21);
					managementPanel.cost=managementPanel.cost+aizen.cost;
				}
			}

			if(command.equals(mitem1Button.getText())){
				if(managementPanel.takeItem[1]==1){
					managementPanel.paintEntake(1);
					managementPanel.cost=managementPanel.cost-bell.cost;
				}
			}
			if(command.equals(mitem2Button.getText())){
				if(managementPanel.takeItem[2]==1){
					managementPanel.paintEntake(2);
					managementPanel.cost=managementPanel.cost-waterBottole.cost;
				}
			}
			if(command.equals(mitem3Button.getText())){
				if(managementPanel.takeItem[3]==1){
					managementPanel.paintEntake(3);
					managementPanel.cost=managementPanel.cost-Oxygentank.cost;
				}
			}
			if(command.equals(mitem4Button.getText())){
				if(managementPanel.takeItem[4]==1){
					managementPanel.paintEntake(4);
					managementPanel.cost=managementPanel.cost-rantan.cost;
				}
			}
			if(command.equals(mitem5Button.getText())){
				if(managementPanel.takeItem[5]==1){
					managementPanel.paintEntake(5);
					managementPanel.cost=managementPanel.cost-reinCoat.cost;
				}
			}
			if(command.equals(mitem6Button.getText())){
				if(managementPanel.takeItem[6]==1){
					managementPanel.paintEntake(6);
					managementPanel.cost=managementPanel.cost-onigiri.cost;
				}
			}
			if(command.equals(mitem7Button.getText())){
				if(managementPanel.takeItem[7]==1){
					managementPanel.paintEntake(7);
					managementPanel.cost=managementPanel.cost-omamori2.cost;
				}
			}
			if(command.equals(mitem8Button.getText())){
				if(managementPanel.takeItem[8]==1){
					managementPanel.paintEntake(8);
					managementPanel.cost=managementPanel.cost-omamori1.cost;
				}
			}
			if(command.equals(mitem9Button.getText())){
				if(managementPanel.takeItem[9]==1){
					managementPanel.paintEntake(9);
					managementPanel.cost=managementPanel.cost-sleepingBag.cost;
				}
			}
			if(command.equals(mitem10Button.getText())){
				if(managementPanel.takeItem[10]==1){
					managementPanel.paintEntake(10);
					managementPanel.cost=managementPanel.cost-misoSoup.cost;
				}
			}
			if(command.equals(mitem11Button.getText())){
				if(managementPanel.takeItem[11]==1){
					managementPanel.paintEntake(11);
					managementPanel.cost=managementPanel.cost-medicine.cost;
				}
			}
			if(command.equals(mitem12Button.getText())){
				if(managementPanel.takeItem[12]==1){
					managementPanel.paintEntake(12);
					managementPanel.cost=managementPanel.cost-headLump.cost;
				}
			}
			if(command.equals(mitem13Button.getText())){
				if(managementPanel.takeItem[13]==1){
					managementPanel.paintEntake(13);
					managementPanel.cost=managementPanel.cost-climbShoes.cost;
				}
			}
			if(command.equals(mitem14Button.getText())){
				if(managementPanel.takeItem[14]==1){
					managementPanel.paintEntake(14);
					managementPanel.cost=managementPanel.cost-kairo.cost;
				}
			}
			if(command.equals(mitem15Button.getText())){
				if(managementPanel.takeItem[15]==1){
					managementPanel.paintEntake(15);
					managementPanel.cost=managementPanel.cost-drink.cost;
				}
			}
			if(command.equals(mitem16Button.getText())){
				if(managementPanel.takeItem[16]==1){
					managementPanel.paintEntake(16);
					managementPanel.cost=managementPanel.cost-coffeeMaker.cost;
				}
			}
			if(command.equals(mitem17Button.getText())){
				if(managementPanel.takeItem[17]==1){
					managementPanel.paintEntake(17);
					managementPanel.cost=managementPanel.cost-miharukasu.cost;
				}
			}
			if(command.equals(mitem18Button.getText())){
				if(managementPanel.takeItem[18]==1){
					managementPanel.paintEntake(18);
					managementPanel.cost=managementPanel.cost-cap.cost;
				}
			}
			if(command.equals(mitem19Button.getText())){
				if(managementPanel.takeItem[19]==1){
					managementPanel.paintEntake(19);
					managementPanel.cost=managementPanel.cost-airBag.cost;
				}
			}
			if(command.equals(mitem20Button.getText())){
				if(managementPanel.takeItem[20]==1){
					managementPanel.paintEntake(20);
					managementPanel.cost=managementPanel.cost-againstColdWear.cost;
				}
			}
			if(command.equals(mitem21Button.getText())){
				if(managementPanel.takeItem[21]==1){
					managementPanel.paintEntake(21);
					managementPanel.cost=managementPanel.cost-aizen.cost;
				}
			}

			if(command.equals(changeIconButton.getText())){
				nowCanvasNumber=11;
				iconPanel.setItem(managementPanel.getItem());
				paintIconCanvas();
			}
			if(command.equals(maintainChangeButton.getText())){

				player.setItem(managementPanel.getItem());
			}
		}
		if(nowCanvasNumber==11){//アイコン変更
			if(command.equals(icon1Button.getText())){
				iconPanel.setIcon(1);
			}
			if(command.equals(icon2Button.getText())){
				iconPanel.setIcon(2);
			}
			if(command.equals(icon3Button.getText())){
				iconPanel.setIcon(3);
			}
			if(command.equals(icon4Button.getText())){
				iconPanel.setIcon(4);
			}
			if(command.equals(icon5Button.getText())){
				iconPanel.setIcon(5);
			}
			if(command.equals(icon6Button.getText())){
				iconPanel.setIcon(6);
			}
			if(command.equals(icon7Button.getText())){
				iconPanel.setIcon(7);
			}
			if(command.equals(icon8Button.getText())){
				iconPanel.setIcon(8);
			}
			if(command.equals(icon9Button.getText())){
				iconPanel.setIcon(9);
			}
			if(command.equals(icon10Button.getText())){
				iconPanel.setIcon(10);
			}
			if(command.equals(backManagementButton.getText())){
				nowCanvasNumber=4;
				managementPanel.setIcon(iconPanel.getIcon());
				managementPanel.change(iconPanel.getIcon());
				for(int i=0;i<22;i++){
					managementPanel.setItem(i,iconPanel.getItem(i));
				}
				paintManagementCanvas();
			}
			if(command.equals(changeButton.getText())){
				managementPanel.setIcon(iconPanel.getIcon());
				managementPanel.change(iconPanel.getIcon());
				for(int i=0;i<22;i++){
					managementPanel.setItem(i,iconPanel.getItem(i));
				}
			}
		}
		if(nowCanvasNumber==5){//ショップ
			if(command.equals(menuButton6.getText())){
				//アイテムとお金の同期
				player.setMoney(shopPanel.getMoney());
				player.setItem(shopPanel.getItem());
				nowCanvasNumber=3;
				for(int i=0;i<22;i++){
					System.out.println(shopPanel.item[i]);
				}
				paintMenuCanvas();
			}
			if(command.equals(nextButton.getText())){
				if(shopPanel.getPageNum()==1){
					shopPanel.paintPanel2();
				}
				if(shopPanel.getPageNum()==2){
					shopPanel.paintPanel3();
				}
				if(shopPanel.getPageNum()==3){
					shopPanel.paintPanel1();
				}
				shopPanel.changePageNum();
			}
			if(command.equals(shop1Button.getText())){
				shopPanel.getItem1();
			}
			if(command.equals(shop2Button.getText())){
				shopPanel.getItem2();
			}
			if(command.equals(shop3Button.getText())){
				shopPanel.getItem3();
			}
			if(command.equals(shop4Button.getText())){
				shopPanel.getItem4();
			}
			if(command.equals(shop5Button.getText())){
				shopPanel.getItem5();
			}
			if(command.equals(shop6Button.getText())){
				shopPanel.getItem6();
			}
			if(command.equals(shop7Button.getText())){
				shopPanel.getItem7();
			}

		}
		if(nowCanvasNumber==6){//富士ステージ詳細
			if(command.equals(menuButton1.getText())){
				nowCanvasNumber=3;
				paintMenuCanvas();
			}
			if(command.equals(startButton1.getText())){
				nowCanvasNumber=9;
				mountain=new Mountain();
				numOfMountain=1;
				gamePanel=new GamePanel(mountain);
				mountain.setItem(managementPanel.takeItem);

				container.add(gamePanel,"game");
				mountain.makeBoard();
				mountain.reTeam();
				mountain.setNumOfMountain(numOfMountain);
				paintGameCanvas();

			}
		}
		if(nowCanvasNumber==7){//エベレストステージ詳細
			if(command.equals(menuButton2.getText())){
				nowCanvasNumber=3;
				paintMenuCanvas();
			}
			if(command.equals(startButton2.getText())){
				nowCanvasNumber=9;
				mountain=new Mountain();
				numOfMountain=2;
				mountain.setItem(managementPanel.takeItem);
				gamePanel=new GamePanel(mountain);
				container.add(gamePanel,"game");
				mountain.makeBoard();
				mountain.reTeam();
				paintGameCanvas();

			}
		}
		if(nowCanvasNumber==8){//マシフステージ詳細
			if(command.equals(menuButton3.getText())){
				nowCanvasNumber=3;
				paintMenuCanvas();
			}
			if(command.equals(startButton3.getText())){
				nowCanvasNumber=9;
				mountain=new Mountain();
				numOfMountain=3;
				mountain.setItem(managementPanel.takeItem);
				gamePanel=new GamePanel(mountain);
				container.add(gamePanel,"game");
				mountain.makeBoard();
				mountain.reTeam();
				paintGameCanvas();

			}
		}
		if(nowCanvasNumber==9){//ゲーム画面


			if(command.equals(ruleButton.getText())){


				mountain.showItem();

				nowCanvasNumber=20;
				paintRuleCanvas();
			}
			if(command.equals(treeButton.getText())){
				gamePanel.setLog(3);
				treeButton.setEnabled(false);
				restButton.setEnabled(false);
				mountain.drawBoard();
				mountain.dice();
				gamePanel.dice=mountain.getDice();
				gamePanel.paintDice();
				mountain.diceCal=mountain.diceCal(mountain.dice);
				mountain.drawSelect(mountain.diceCal);
				mountain.drawSelect2(mountain.diceCal2);
				gamePanel.selectNum=mountain.selectNum;
				gamePanel.paintSelectNum();

				//バーストしたかどうかの判定
				if(gamePanel.checkBurst()==1){
					yamabikoNum=0;
					gamePanel.setLog(1);
					System.out.println("バーストしました");
					mountain.burst();
					mountain.addTime();
					treeButton.setEnabled(true);
					gamePanel.renewButton();
				}

			}
			if(command.equals(roadSelectButton1.getText())){
				gamePanel.setLog(2);
				gamePanel.enabledButton();
				if(yamabikoNum!=0){
					mountain.proceedFrame(1);
					yamabikoNum--;
				}
				else{
					mountain.proceedFrame(1);
				}
				mountain.addTime();
				gamePanel.eventNumber=mountain.eventNum();
				//2チームゴールしたかどうかの判定
				if(mountain.checkFinish()==1){
					nowCanvasNumber=10;
					time=mountain.getTime();
					overNumber=1;
					overLog=mountain.getOverLog(overNumber);
					paintGameOverCanvas();
					gameOverPanel.paint();
				}
				if(gamePanel.eventNumber!=-1){
					if(gamePanel.eventNumber==13||gamePanel.eventNumber==16){
						nowCanvasNumber=10;
						time=mountain.getTime();
						overNumber=gamePanel.eventNumber;
						overLog=mountain.getOverLog(overNumber);
						paintGameOverCanvas();
						gameOverPanel.paint();
					}

					//イベントを発生させている
					mountain.event(gamePanel.eventNumber);
					//イベントのログ
					gamePanel.setLog(gamePanel.eventNumber);
				}
				gamePanel.renewButton();
			}
			if(command.equals(roadSelectButton2.getText())){
				gamePanel.setLog(2);
				gamePanel.enabledButton();
				if(yamabikoNum!=0){
					mountain.proceedFrame(2);
					yamabikoNum--;
				}
				else{
					mountain.proceedFrame(2);
				}
				mountain.addTime();
				gamePanel.eventNumber=mountain.eventNum();
				//2チームゴールしたかどうかの判定
				if(mountain.checkFinish()==1){
					nowCanvasNumber=10;
					time=mountain.getTime();
					overNumber=1;
					overLog=mountain.getOverLog(overNumber);
					paintGameOverCanvas();
					gameOverPanel.paint();
				}
				if(gamePanel.eventNumber!=-1){
					if(gamePanel.eventNumber==13||gamePanel.eventNumber==16){
						nowCanvasNumber=10;
						time=mountain.getTime();
						overNumber=gamePanel.eventNumber;
						overLog=mountain.getOverLog(overNumber);
						paintGameOverCanvas();
						gameOverPanel.paint();
					}
					mountain.event(gamePanel.eventNumber);
					gamePanel.setLog(gamePanel.eventNumber);
				}
				gamePanel.renewButton();
			}
			if(command.equals(roadSelectButton3.getText())){
				gamePanel.setLog(2);
				gamePanel.enabledButton();
				if(yamabikoNum!=0){
					mountain.proceedFrame(3);
					yamabikoNum--;
				}
				else{
					mountain.proceedFrame(3);
				}
				mountain.addTime();
				gamePanel.eventNumber=mountain.eventNum();
				//2チームゴールしたかどうかの判定
				if(mountain.checkFinish()==1){
					nowCanvasNumber=10;
					time=mountain.getTime();
					overNumber=1;
					overLog=mountain.getOverLog(overNumber);
					paintGameOverCanvas();
					gameOverPanel.paint();
				}
				if(gamePanel.eventNumber!=-1){
					if(gamePanel.eventNumber==13||gamePanel.eventNumber==16){
						nowCanvasNumber=10;
						time=mountain.getTime();
						overNumber=gamePanel.eventNumber;
						overLog=mountain.getOverLog(overNumber);
						paintGameOverCanvas();
						gameOverPanel.paint();
					}
					mountain.event(gamePanel.eventNumber);
					gamePanel.setLog(gamePanel.eventNumber);
				}
				gamePanel.renewButton();
			}
			if(command.equals(roadSelectButton4.getText())){
				gamePanel.setLog(2);
				gamePanel.enabledButton();
				if(yamabikoNum!=0){
					mountain.proceedFrame(4);
					yamabikoNum--;
				}
				else{
					mountain.proceedFrame(4);
				}
				mountain.addTime();
				gamePanel.eventNumber=mountain.eventNum();
				//2チームゴールしたかどうかの判定
				if(mountain.checkFinish()==1){
					nowCanvasNumber=10;
					time=mountain.getTime();
					overNumber=1;
					overLog=mountain.getOverLog(overNumber);
					paintGameOverCanvas();
					gameOverPanel.paint();
				}
				if(gamePanel.eventNumber!=-1){
					if(gamePanel.eventNumber==13||gamePanel.eventNumber==16){
						nowCanvasNumber=10;
						time=mountain.getTime();
						overNumber=gamePanel.eventNumber;
						overLog=mountain.getOverLog(overNumber);
						paintGameOverCanvas();
						gameOverPanel.paint();
					}
					mountain.event(gamePanel.eventNumber);
					gamePanel.setLog(gamePanel.eventNumber);
				}
				gamePanel.renewButton();
			}
			if(command.equals(roadSelectButton5.getText())){
				gamePanel.setLog(2);
				gamePanel.enabledButton();
				if(yamabikoNum!=0){
					mountain.proceedFrame(5);
					yamabikoNum--;
				}
				else{
					mountain.proceedFrame(5);
				}
				mountain.addTime();
				gamePanel.eventNumber=mountain.eventNum();
				//2チームゴールしたかどうかの判定
				if(mountain.checkFinish()==1){
					nowCanvasNumber=10;
					time=mountain.getTime();
					overNumber=1;
					overLog=mountain.getOverLog(overNumber);
					paintGameOverCanvas();
					gameOverPanel.paint();
				}
				if(gamePanel.eventNumber!=-1){
					if(gamePanel.eventNumber==13||gamePanel.eventNumber==16){
						nowCanvasNumber=10;
						time=mountain.getTime();
						overNumber=gamePanel.eventNumber;
						overLog=mountain.getOverLog(overNumber);
						paintGameOverCanvas();
						gameOverPanel.paint();
					}
					mountain.event(gamePanel.eventNumber);
					gamePanel.setLog(gamePanel.eventNumber);
				}
				gamePanel.renewButton();
			}
			if(command.equals(roadSelectButton6.getText())){
				gamePanel.setLog(2);
				gamePanel.enabledButton();
				if(yamabikoNum!=0){
					mountain.proceedFrame(6);
					yamabikoNum--;
				}
				else{
					mountain.proceedFrame(6);
				}
				mountain.addTime();
				gamePanel.eventNumber=mountain.eventNum();
				//2チームゴールしたかどうかの判定
				if(mountain.checkFinish()==1){
					nowCanvasNumber=10;
					time=mountain.getTime();
					overNumber=1;
					overLog=mountain.getOverLog(overNumber);
					paintGameOverCanvas();
					gameOverPanel.paint();
				}
				if(gamePanel.eventNumber!=-1){
					if(gamePanel.eventNumber==13||gamePanel.eventNumber==16){
						nowCanvasNumber=10;
						time=mountain.getTime();
						overNumber=gamePanel.eventNumber;
						overLog=mountain.getOverLog(overNumber);
						paintGameOverCanvas();
						gameOverPanel.paint();
					}
					mountain.event(gamePanel.eventNumber);
					gamePanel.setLog(gamePanel.eventNumber);
				}
				gamePanel.renewButton();
			}


			if(command.equals(itemButton.getText())){

				paintItemCanvas();
			}
			if(command.equals(yamabikoButton.getText())){

				//やまびこのクラス
				Play m_CPlay = new Play();
		        Recorder m_CRec = new Recorder();
				Thread t = new Thread();

		        // スレッド開始
		        m_CPlay.start();
		        m_CRec.start();
		        t.start();

		        System.out.println( "開始" );

		        int count = 0;
		         while( true )
		        {
		            try
		            {
		                // 音声コピー
		                m_CPlay.setVoice( m_CRec.getVoice() );

		                // ms停止
		                Thread.sleep( 300 );
		            } catch (InterruptedException er) {

		            }

		            // 5秒ループ
		            count++;
		            if( count > ( 25 ) )
		            {
		                m_CPlay.end();
		                m_CRec.end();
		            }

		            // 脱出
		            if( !m_CPlay.g_bPlayer && !m_CRec.g_bRecorder )
		                break;
		        }
		         yamabikoButton.setEnabled(false);
		         Random rnd = new Random();
		         yamabikoNum= rnd.nextInt(2)+1;
			}
			if(command.equals(restButton.getText())){
				mountain.rest();
				mountain.addTime();
				gamePanel.renewButton();

			}
		}
		if(nowCanvasNumber==10){//ゲームオーバー画面
			if(command.equals(okButton.getText())){
				nowCanvasNumber=14;
				managementPanel.useItem();
				managementPanel.reset();
				scorePanel=new ScorePanel();
				container.add(scorePanel,"score");
				paintScoreCanvas();
				if(overNumber!=1){
					sendMessage("getScore");
					String a=getMessage();
					int k=Integer.parseInt(a);
					String s;
					score_array=new ArrayList<Score>();
					for(int i=0;i<k;i++){
						s=getMessage();
						score_array.add(new Score(s));
						}
					for(int i=0;i<score_array.size();i++){
						if(score_array.get(i).getStage()==1){

							scorePanel.paintRank(score_array.get(i).getName(),score_array.get(i).getScore());
						}
					}
					scorePanel.score(0);
				}
				else{
					sendMessage("scoreUpdate");
					/*int[] b=new int[22];
					b=mountain.getItem();
					String mountainItem="0"+b[0];*/
					String data=String.valueOf(mountain.getNumOfMountain())+"/11/"
							+ player.getName()+"/"+mountain.getTime()+"/"+"01.01.00.00.02.03.00.00";
					System.out.println(data);
					sendMessage(data);
					System.out.println(getMessage());
					String a=getMessage();
					System.out.println("aは"+a);
					int k=Integer.parseInt(a);
					String s;
					score_array=new ArrayList<Score>();
					for(int i=0;i<k;i++){
						s=getMessage();
						score_array.add(new Score(s));

					}

					for(int i=0;i<score_array.size();i++){
						if(score_array.get(i).getStage()==mountain.getNumOfMountain()){

							scorePanel.paintRank(score_array.get(i).getName(),score_array.get(i).getScore());
						}
					}
					scorePanel.score(mountain.getTime());
				}
			}
		}

		if(nowCanvasNumber==12){//アイテム

		}
		if(nowCanvasNumber==13){//ルール説明
			if(command.equals(backToButton.getText())){
				nowCanvasNumber=3;
				paintMenuCanvas();
			}
		}
		if(nowCanvasNumber==14){//スコア表示
			if(command.equals(menuButton5.getText())){
				nowCanvasNumber=3;
				paintMenuCanvas();
			}
			if(command.equals(endButton.getText())){
				try {
					reader.close();
					writer.close();
				} catch (IOException e1) {
					// TODO 自動生成された catch ブロック
					e1.printStackTrace();
				}
				try {
					socket.close();
					System.out.println("ソケットを閉じました");
				} catch (IOException e1) {
				}
				System.exit(0);
			}
		}
		if(nowCanvasNumber==20){//ルール説明
			if(command.equals(backToButton.getText())){
				nowCanvasNumber=9;
				paintGameCanvas();
			}
		}
	}
	public void paintTitleCanvas(){
			//container.removeAll();

			layout.show(container,"title");

	}

	public class TitlePanel extends JPanel{


		JLabel label0;
		JLabel label1;
		public TitlePanel(){

			setLayout(null);


			climbButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			climbButton.setForeground(Color.BLACK);
			climbButton.setBounds(420, 400, 130, 50);
			climbButton.setBackground(Color.WHITE);
			this.add(climbButton);

			endButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			endButton.setForeground(Color.BLACK);
			endButton.setBounds(420, 470, 130, 50);
			endButton.setBackground(Color.WHITE);
			this.add(endButton);
			//テキスト1
			label1 = new JLabel("MountainGO!");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 70));
			label1.setForeground(new Color(70,140,0));
			label1.setBounds(240, 100, 500, 100);
			this.add(label1);

			label0 = new JLabel(bgTitle);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);

		}
	}

	public void paintRoginCanvas(){

		layout.show(container,"rogin");


	}


	public class RoginPanel extends JPanel {


		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;


		public RoginPanel(){
			System.out.println("ログイン");
			setLayout(null);



			//プレイヤ名入力
			label1 = new JLabel("名前、パスワードを入力して下さい");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label1.setBounds(40, 10, 500, 50);
			this.add(label1);

			label2 = new JLabel("名前");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label2.setBounds(325, 350, 500, 50);
			this.add(label2);

			label3 = new JLabel("パスワード");
			label3.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label3.setBounds(325, 420, 500, 50);
			this.add(label3);

			playerName1.setFont(new Font("メイリオ", Font.BOLD, 15));
			playerName1.setHorizontalAlignment(JTextField.CENTER);
			playerName1.setBackground(Color.WHITE);
			playerName1.setBounds(325, 390, 280, 30);
			this.add(playerName1);


			password1.setFont(new Font("メイリオ", Font.BOLD, 15));
			password1.setHorizontalAlignment(JTextField.CENTER);
			password1.setBackground(Color.WHITE);
			password1.setBounds(325, 460, 280, 30);
			this.add(password1);


			newAccountButton1.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			newAccountButton1.setForeground(Color.BLACK);
			newAccountButton1.setBounds(310, 520, 130, 50);
			newAccountButton1.setBackground(Color.WHITE);
			this.add(newAccountButton1);


			roginButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roginButton.setForeground(Color.BLACK);
			roginButton.setBounds(480, 520, 130, 50);
			roginButton.setBackground(Color.WHITE);
			this.add(roginButton);

			label0 = new JLabel(bgRogin);
			label0.setBounds(0, 0, 1000, 700);

			this.add(label0);
		}
		public String getName(){
			return playerName1.getText();
		}
		public String getPass(){
			return String.valueOf(password1.getPassword());
		}
	}

	public void paintNaccountCanvas(){

		layout.show(container,"naccount");
	}


	public class NaccountPanel extends JPanel {


		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;


		public NaccountPanel(){

			setLayout(null);
			System.out.println("新規");

			//プレイヤ名入力
			label1 = new JLabel("名前、パスワードを入力して下さい");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label1.setBounds(40, 10, 500, 50);
			this.add(label1);

			label2 = new JLabel("名前");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label2.setBounds(325, 350, 500, 50);
			this.add(label2);

			label3 = new JLabel("パスワード");
			label3.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label3.setBounds(325, 420, 500, 50);
			this.add(label3);
			label5 = new JLabel("");
			label5.setBackground(Color.RED);
			label5.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label5.setBounds(325, 480, 500, 50);
			this.add(label5);
			playerName2 = new JTextField("",20);
			playerName2.setFont(new Font("メイリオ", Font.BOLD, 15));
			playerName2.setHorizontalAlignment(JTextField.CENTER);
			playerName2.setBackground(Color.WHITE);
			playerName2.setBounds(325, 390, 280, 30);
			this.add(playerName2);

			password2 = new JPasswordField(20);
			password2.setFont(new Font("メイリオ", Font.BOLD, 15));
			password2.setHorizontalAlignment(JTextField.CENTER);
			password2.setBackground(Color.WHITE);
			password2.setBounds(325, 460, 280, 30);
			this.add(password2);


			newAccountButton2.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			newAccountButton2.setForeground(Color.BLACK);
			newAccountButton2.setBounds(400, 520, 130, 50);
			newAccountButton2.setBackground(Color.WHITE);
			this.add(newAccountButton2);


			label0 = new JLabel(bgRogin);
			label0.setBounds(0, 0, 1000, 700);

			this.add(label0);
		}
		public String getName(){
			return playerName2.getText();
		}
		public String getPass(){
			return String.valueOf(password2.getPassword());
		}
		public void reEnter(){
			label5.setText("入力しなおしてください");
			this.validate();
		}
	}

	public void paintMenuCanvas(){
		layout.show(container,"menu");
	}

	public class MenuPanel extends JPanel {

		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;
		JLabel label9;
		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		JLabel label18;


		MenuPanel(){

			setLayout(null);
			//山の写真


			label4 = new JLabel("富士山");
			label4.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label4.setOpaque(true);
			label4.setBackground(new Color(0,255,153));
			label4.setBounds(320, 50, 64, 30);
			this.add(label4);

			label5 = new JLabel("エベレスト");
			label5.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label5.setOpaque(true);
			label5.setBackground(new Color(0,255,153));
			label5.setBounds(760, 50, 83, 30);
			this.add(label5);

			label6 = new JLabel("ヴィンソン・マシフ");
			label6.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label6.setOpaque(true);
			label6.setBackground(new Color(0,255,153));
			label6.setBounds(280, 360, 140, 30);
			this.add(label6);

			label1 = new JLabel(mtFuzi);
			label1.setBounds(50, 70, 179,120);
			this.add(label1);
			label2 = new JLabel(mtEv);
			label2.setBounds(500, 70, 179,120);
			this.add(label2);
			label3 = new JLabel(mtMa);
			label3.setBounds(50, 380, 179,120);
			this.add(label3);

			label10 = new JLabel("言わずとも知れた");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label10.setBounds(290, 105, 264, 30);
			this.add(label10);
			label11 = new JLabel("日本最高峰の山");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label11.setBounds(290, 155, 264, 30);
			this.add(label11);
			label12 = new JLabel("標高：3776m");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label12.setBounds(290, 235, 264, 30);
			this.add(label12);

			label13 = new JLabel("またの名をチョモランマ");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label13.setBounds(720, 105, 264, 30);
			this.add(label13);
			label13 = new JLabel("年々地殻変動で標高が変わる");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 18));
			label13.setBounds(695, 155, 264, 30);
			this.add(label13);
			label14 = new JLabel("標高：8844m");
			label14.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label14.setBounds(730, 235, 264, 30);
			this.add(label14);

			label15 = new JLabel("南極大陸最高峰の山。");
			label15.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label15.setBounds(270, 420, 264, 30);
			this.add(label15);
			label16 = new JLabel("７大陸最高峰の一つ。");
			label16.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label16.setBounds(270, 470, 264, 30);
			this.add(label16);
			label17 = new JLabel("標高：4892m");
			label17.setFont(new Font("Meiryo UI", Font.BOLD, 19));
			label17.setBounds(280, 550, 264, 30);
			this.add(label17);

			label18 = new JLabel("行きたい山の写真をクリックしてください");
			label18.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label18.setForeground(Color.RED);
			label18.setBounds(280, 625, 350, 30);
			this.add(label18);

			accountManagementButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			accountManagementButton.setForeground(Color.BLACK);
			accountManagementButton.setBounds(680, 350, 250, 50);
			accountManagementButton.setBackground(Color.WHITE);
			this.add(accountManagementButton);

			shopButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shopButton.setForeground(Color.BLACK);
			shopButton.setBounds(680, 420, 250, 50);
			shopButton.setBackground(Color.WHITE);
			this.add(shopButton);

			ruleButton1.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			ruleButton1.setForeground(Color.BLACK);
			ruleButton1.setBounds(680, 490, 250, 50);
			ruleButton1.setBackground(Color.WHITE);
			this.add(ruleButton1);

			titleButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			titleButton.setForeground(Color.BLACK);
			titleButton.setBounds(680, 560, 250, 50);
			titleButton.setBackground(Color.WHITE);
			this.add(titleButton);



			label7 = new JLabel(bgText);
			label7.setBounds(233, 48, 252,270);
			this.add(label7);
			label8 = new JLabel(bgText);
			label8.setBounds(683, 48, 252,270);
			this.add(label8);
			label9 = new JLabel(bgText);
			label9.setBounds(233, 355, 252,270);
			this.add(label9);

			fuziSelectButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			fuziSelectButton.setBounds(50, 70, 179,120);
			fuziSelectButton.setOpaque(false);
			this.add(fuziSelectButton);
			evSelectButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			evSelectButton.setBounds(500, 70, 179,120);
			evSelectButton.setOpaque(false);
			this.add(evSelectButton);
			maSelectButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			maSelectButton.setBounds(50, 380, 179,120);
			maSelectButton.setOpaque(false);
			this.add(maSelectButton);


			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000,700);
			this.add(label0);
		}
	}

	public void paintManagementCanvas(){
		layout.show(container,"management");
	}

	public class ManagementPanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;

		JLabel label10;

		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		JLabel label18;
		JLabel label19;
		JLabel label20;
		JLabel label21;
		JLabel label22;
		JLabel label23;
		JLabel label24;
		JLabel label25;
		JLabel label26;
		JLabel label27;
		JLabel label28;
		JLabel label29;
		JLabel label30;
		JLabel label31;
		JLabel label40;

		JLabel label41;
		JLabel label42;
		JLabel label43;
		JLabel label44;
		JLabel label45;
		JLabel label46;
		JLabel label47;
		JLabel label48;
		JLabel label49;
		JLabel label50;
		JLabel label51;
		JLabel label52;
		JLabel label53;
		JLabel label54;
		JLabel label55;
		JLabel label56;
		JLabel label57;
		JLabel label58;
		JLabel label59;
		JLabel label60;
		JLabel label61;

		JLabel label71;
		JLabel label72;
		JLabel label73;
		JLabel label74;
		JLabel label75;
		JLabel label76;
		JLabel label77;
		JLabel label78;
		JLabel label79;
		JLabel label80;
		JLabel label81;
		JLabel label82;
		JLabel label83;
		JLabel label84;
		JLabel label85;
		JLabel label86;
		JLabel label87;
		JLabel label88;
		JLabel label89;
		JLabel label90;
		JLabel label91;

		String name="a";
		int money=0;
		int cost=0;
		int[] item = new int[22];
		int[] takeItem=new int[22];//持っていくアイテム（0は持って行かない。1は持っていく）
		public ManagementPanel(){
			setLayout(null);
			for(int i=0;i<22;i++){
				takeItem[i]=0;
			}
			changeIconButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			changeIconButton.setBounds(50, 200, 180,80);
			changeIconButton.setOpaque(false);
			this.add(changeIconButton);
			backButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			backButton.setBounds(270, 580, 100,40);
			backButton.setOpaque(false);
			//this.add(backButton);
			frontButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			frontButton.setBounds(390, 580, 100,40);
			frontButton.setOpaque(false);
			//this.add(frontButton);
			resetButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			resetButton.setBounds(680, 580, 150,40);
			resetButton.setOpaque(false);
			//this.add(resetButton);
			maintainChangeButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			maintainChangeButton.setBounds(30, 510, 220,50);
			maintainChangeButton.setOpaque(false);
			this.add(maintainChangeButton);
			menuButton4.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			menuButton4.setBounds(30, 580, 220,50);
			menuButton4.setOpaque(false);
			this.add(menuButton4);


			pitem1Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem1Button.setBounds(550, 60, 20,10);
			this.add(pitem1Button);
			pitem2Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem2Button.setBounds(550, 85, 20,10);
			this.add(pitem2Button);
			pitem3Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem3Button.setBounds(550, 110, 20,10);
			this.add(pitem3Button);
			pitem4Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem4Button.setBounds(550, 135, 20,10);
			this.add(pitem4Button);
			pitem5Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem5Button.setBounds(550, 160, 20,10);
			this.add(pitem5Button);
			pitem6Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem6Button.setBounds(550, 185, 20,10);
			this.add(pitem6Button);
			pitem7Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem7Button.setBounds(550, 210, 20,10);
			this.add(pitem7Button);
			pitem8Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem8Button.setBounds(550, 235, 20,10);
			this.add(pitem8Button);
			pitem9Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem9Button.setBounds(550, 260, 20,10);
			this.add(pitem9Button);
			pitem10Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem10Button.setBounds(550, 285, 20,10);
			this.add(pitem10Button);
			pitem11Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem11Button.setBounds(550, 310, 20,10);
			this.add(pitem11Button);
			pitem12Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem12Button.setBounds(550, 335, 20,10);
			this.add(pitem12Button);
			pitem13Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem13Button.setBounds(550, 360, 20,10);
			this.add(pitem13Button);
			pitem14Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem14Button.setBounds(550, 385, 20,10);
			this.add(pitem14Button);
			pitem15Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem15Button.setBounds(550, 410, 20,10);
			this.add(pitem15Button);
			pitem16Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem16Button.setBounds(550, 435, 20,10);
			this.add(pitem16Button);
			pitem17Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem17Button.setBounds(550, 460, 20,10);
			this.add(pitem17Button);
			pitem18Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem18Button.setBounds(550, 485, 20,10);
			this.add(pitem18Button);
			pitem19Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem19Button.setBounds(550, 510, 20,10);
			this.add(pitem19Button);
			pitem20Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem20Button.setBounds(550, 535, 20,10);
			this.add(pitem20Button);
			pitem21Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			pitem21Button.setBounds(550, 560, 20,10);
			this.add(pitem21Button);

			mitem1Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem1Button.setBounds(580, 60, 20,10);
			this.add(mitem1Button);
			mitem2Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem2Button.setBounds(580, 85, 20,10);
			this.add(mitem2Button);
			mitem3Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem3Button.setBounds(580, 110, 20,10);
			this.add(mitem3Button);
			mitem4Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem4Button.setBounds(580, 135, 20,10);
			this.add(mitem4Button);
			mitem5Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem5Button.setBounds(580, 160, 20,10);
			this.add(mitem5Button);
			mitem6Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem6Button.setBounds(580, 185, 20,10);
			this.add(mitem6Button);
			mitem7Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem7Button.setBounds(580, 210, 20,10);
			this.add(mitem7Button);
			mitem8Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem8Button.setBounds(580, 235, 20,10);
			this.add(mitem8Button);
			mitem9Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem9Button.setBounds(580, 260, 20,10);
			this.add(mitem9Button);
			mitem10Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem10Button.setBounds(580, 285, 20,10);
			this.add(mitem10Button);
			mitem11Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem11Button.setBounds(580, 310, 20,10);
			this.add(mitem11Button);
			mitem12Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem12Button.setBounds(580, 335, 20,10);
			this.add(mitem12Button);
			mitem13Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem13Button.setBounds(580, 360, 20,10);
			this.add(mitem13Button);
			mitem14Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem14Button.setBounds(580, 385, 20,10);
			this.add(mitem14Button);
			mitem15Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem15Button.setBounds(580, 410, 20,10);
			this.add(mitem15Button);
			mitem16Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem16Button.setBounds(580, 435, 20,10);
			this.add(mitem16Button);
			mitem17Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem17Button.setBounds(580, 460, 20,10);
			this.add(mitem17Button);
			mitem18Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem18Button.setBounds(580, 485, 20,10);
			this.add(mitem18Button);
			mitem19Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem19Button.setBounds(580, 510, 20,10);
			this.add(mitem19Button);
			mitem20Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem20Button.setBounds(580, 535, 20,10);
			this.add(mitem20Button);
			mitem21Button.setFont(new Font("MYSTICAL", Font.BOLD, 10));
			mitem21Button.setBounds(580, 560, 20,10);
			this.add(mitem21Button);

			label40 = new JLabel("アイテム                所持数                  選択 解除");
			label40.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label40.setBounds(280, 40, 400, 15);
			this.add(label40);

			label11 = new JLabel("鈴(2)");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label11.setBounds(280, 60, 115, 10);
			this.add(label11);
			label12 = new JLabel("水筒(2)");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label12.setBounds(280, 85, 115, 10);
			this.add(label12);
			label13 = new JLabel("酸素ボンベ(3)");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label13.setBounds(280, 110, 115, 10);
			this.add(label13);
			label14 = new JLabel("ランタン(2)");
			label14.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label14.setBounds(280, 135, 115, 10);
			this.add(label14);
			label15 = new JLabel("レインウェア(1)");
			label15.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label15.setBounds(280, 160, 115, 10);
			this.add(label15);
			label16 = new JLabel("おかん特製おにぎり(3)");
			label16.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label16.setBounds(280, 185, 115, 10);
			this.add(label16);
			label17 = new JLabel("豪運のお守り(2)");
			label17.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label17.setBounds(280, 210, 115, 10);
			this.add(label17);
			label18 = new JLabel("金運のお守り(1)");
			label18.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label18.setBounds(280, 235, 115, 10);
			this.add(label18);
			label19 = new JLabel("高級寝袋(3)");
			label19.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label19.setBounds(280, 260, 115, 10);
			this.add(label19);
			label20 = new JLabel("おかん特製味噌汁(3)");
			label20.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label20.setBounds(280, 285, 115, 10);
			this.add(label20);
			label21 = new JLabel("高山病の薬(１)");
			label21.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label21.setBounds(280, 310, 115, 10);
			this.add(label21);
			label22 = new JLabel("ヘッドランプ(2)");
			label22.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label22.setBounds(280, 335, 115, 10);
			this.add(label22);
			label23 = new JLabel("高級登山靴(2)");
			label23.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label23.setBounds(280, 360, 115, 10);
			this.add(label23);
			label24 = new JLabel("ホッカイロ(2)");
			label24.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label24.setBounds(280, 385, 115, 10);
			this.add(label24);
			label25 = new JLabel("栄養ドリンク(4)");
			label25.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label25.setBounds(280, 410, 115, 10);
			this.add(label25);
			label26 = new JLabel("コーヒーメーカー(2)");
			label26.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label26.setBounds(280, 435, 115, 10);
			this.add(label26);
			label27 = new JLabel("みはるかすのCD(0)");
			label27.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label27.setBounds(280, 460, 115, 10);
			this.add(label27);
			label28 = new JLabel("帽子(1)");
			label28.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label28.setBounds(280, 485, 115, 10);
			this.add(label28);
			label29 = new JLabel("火薬式エアバッグ(2)");
			label29.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label29.setBounds(280, 510, 115, 10);
			this.add(label29);
			label30 = new JLabel("防寒具(2)");
			label30.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label30.setBounds(280, 535, 115, 10);
			this.add(label30);
			label31 = new JLabel("アイゼン(2)");
			label31.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label31.setBounds(280, 560, 115, 10);
			this.add(label31);

			label41 = new JLabel("");
			label41.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label41.setBounds(480, 60, 115, 10);
			this.add(label41);
			label42 = new JLabel("");
			label42.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label42.setBounds(480, 85, 115, 10);
			this.add(label42);
			label43 = new JLabel("");
			label43.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label43.setBounds(480, 110, 115, 10);
			this.add(label43);
			label44 = new JLabel("");
			label44.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label44.setBounds(480, 135, 115, 10);
			this.add(label44);
			label45 = new JLabel("");
			label45.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label45.setBounds(480, 160, 115, 10);
			this.add(label45);
			label46 = new JLabel("");
			label46.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label46.setBounds(480, 185, 115, 10);
			this.add(label46);
			label47 = new JLabel("");
			label47.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label47.setBounds(480, 210, 115, 10);
			this.add(label47);
			label48 = new JLabel("");
			label48.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label48.setBounds(480, 235, 115, 10);
			this.add(label48);
			label49 = new JLabel("");
			label49.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label49.setBounds(480, 260, 115, 10);
			this.add(label49);
			label50 = new JLabel("");
			label50.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label50.setBounds(480, 285, 115, 10);
			this.add(label50);
			label51 = new JLabel("");
			label51.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label51.setBounds(480, 310, 115, 10);
			this.add(label51);
			label52 = new JLabel("");
			label52.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label52.setBounds(480, 335, 115, 10);
			this.add(label52);
			label53 = new JLabel("");
			label53.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label53.setBounds(480, 360, 115, 10);
			this.add(label53);
			label54 = new JLabel("");
			label54.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label54.setBounds(480, 385, 115, 10);
			this.add(label54);
			label55 = new JLabel("");
			label55.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label55.setBounds(480, 410, 115, 10);
			this.add(label55);
			label56 = new JLabel("");
			label56.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label56.setBounds(480, 435, 115, 10);
			this.add(label56);
			label57 = new JLabel("");
			label57.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label57.setBounds(480, 460, 115, 10);
			this.add(label57);
			label58 = new JLabel("");
			label58.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label58.setBounds(480, 485, 115, 10);
			this.add(label58);
			label59 = new JLabel("");
			label59.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label59.setBounds(480, 510, 115, 10);
			this.add(label59);
			label60 = new JLabel("");
			label60.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label60.setBounds(480, 535, 115, 10);
			this.add(label60);
			label61 = new JLabel("");
			label61.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label61.setBounds(480, 560, 115, 10);
			this.add(label61);

			label71 = new JLabel("0");
			label71.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label71.setBounds(431, 60, 115, 10);
			this.add(label71);
			label72 = new JLabel("0");
			label72.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label72.setBounds(431, 85, 115, 10);
			this.add(label72);
			label73 = new JLabel("0");
			label73.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label73.setBounds(431, 110, 115, 10);
			this.add(label73);
			label74 = new JLabel("0");
			label74.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label74.setBounds(431, 135, 115, 10);
			this.add(label74);
			label75 = new JLabel("0");
			label75.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label75.setBounds(431, 160, 115, 10);
			this.add(label75);
			label76 = new JLabel("0");
			label76.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label76.setBounds(431, 185, 115, 10);
			this.add(label76);
			label77 = new JLabel("0");
			label77.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label77.setBounds(431, 210, 115, 10);
			this.add(label77);
			label78 = new JLabel("0");
			label78.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label78.setBounds(431, 235, 115, 10);
			this.add(label78);
			label79 = new JLabel("0");
			label79.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label79.setBounds(431, 260, 115, 10);
			this.add(label79);
			label80 = new JLabel("0");
			label80.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label80.setBounds(431, 285, 115, 10);
			this.add(label80);
			label81 = new JLabel("0");
			label81.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label81.setBounds(431, 310, 115, 10);
			this.add(label81);
			label82 = new JLabel("0");
			label82.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label82.setBounds(431, 335, 115, 10);
			this.add(label82);
			label83 = new JLabel("0");
			label83.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label83.setBounds(431, 360, 115, 10);
			this.add(label83);
			label84 = new JLabel("0");
			label84.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label84.setBounds(431, 385, 115, 10);
			this.add(label84);
			label85 = new JLabel("0");
			label85.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label85.setBounds(431, 410, 115, 10);
			this.add(label85);
			label86 = new JLabel("0");
			label86.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label86.setBounds(431, 435, 115, 10);
			this.add(label86);
			label87 = new JLabel("0");
			label87.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label87.setBounds(431, 460, 115, 10);
			this.add(label87);
			label88 = new JLabel("0");
			label88.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label88.setBounds(431, 485, 115, 10);
			this.add(label88);
			label89 = new JLabel("0");
			label89.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label89.setBounds(431, 510, 115, 10);
			this.add(label89);
			label90 = new JLabel("0");
			label90.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label90.setBounds(431, 535, 115, 10);
			this.add(label90);
			label91 = new JLabel("0");
			label91.setFont(new Font("Meiryo UI", Font.BOLD, 10));
			label91.setBounds(431, 560, 115, 10);
			this.add(label91);
			label1 = new JLabel("アイテム");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label1.setOpaque(true);
			label1.setBackground(new Color(0,255,153));
			label1.setBounds(400, 5, 75, 30);
			this.add(label1);

			label2 = new JLabel("持っていくもの");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label2.setOpaque(true);
			label2.setBackground(new Color(0,255,153));
			label2.setBounds(700, 5, 120, 30);
			//this.add(label2);

			label3 = new JLabel("名前：");
			label3.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label3.setBounds(50, 340, 120, 30);
			this.add(label3);

			label4 = new JLabel("所持金：            円");
			label4.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label4.setBounds(50, 400, 300, 30);
			this.add(label4);

			label7 = new JLabel(name);
			label7.setHorizontalAlignment(JLabel.LEFT);
			label7.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label7.setBounds(110, 340, 120, 30);
			this.add(label7);

			String a=String.valueOf(money);
			label8 = new JLabel(a);
			label8.setHorizontalAlignment(JLabel.RIGHT);
			label8.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label8.setBounds(55, 400, 150, 30);
			this.add(label8);

			label10 = new JLabel(icon1);
			label10.setBounds(63, 40, 150, 150);
			this.add(label10);

			label5 = new JLabel(managementWood);
			label5.setBounds(260, 30, 350, 600);
			this.add(label5);

			label6 = new JLabel(managementWood);
			label6.setBounds(625, 30, 350, 600);
			//this.add(label6);

			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);
		}

		public void useItem(){
			for(int i=0;i<22;i++){
				if(takeItem[i]==1){
					managementPanel.item[i]--;
					player.setItem(managementPanel.getItem());
				}
			}
		}
		public void reset(){
			label41.setText("");
			label42.setText("");
			label43.setText("");
			label44.setText("");
			label45.setText("");
			label46.setText("");
			label47.setText("");
			label48.setText("");
			label49.setText("");
			label50.setText("");
			label51.setText("");
			label52.setText("");
			label53.setText("");
			label54.setText("");
			label55.setText("");
			label56.setText("");
			label57.setText("");
			label58.setText("");
			label59.setText("");
			label60.setText("");
			label61.setText("");

			for(int i=0;i<22;i++){
				this.takeItem[i]=0;
				this.cost=0;
			}
		}
		public void paintHavingNum(){
			label71.setText(String.valueOf(this.item[1]));
			label72.setText(String.valueOf(this.item[2]));
			label73.setText(String.valueOf(this.item[3]));
			label74.setText(String.valueOf(this.item[4]));
			label75.setText(String.valueOf(this.item[5]));
			label76.setText(String.valueOf(this.item[6]));
			label77.setText(String.valueOf(this.item[7]));
			label78.setText(String.valueOf(this.item[8]));
			label79.setText(String.valueOf(this.item[9]));
			label80.setText(String.valueOf(this.item[10]));
			label81.setText(String.valueOf(this.item[11]));
			label82.setText(String.valueOf(this.item[12]));
			label83.setText(String.valueOf(this.item[13]));
			label84.setText(String.valueOf(this.item[14]));
			label85.setText(String.valueOf(this.item[15]));
			label86.setText(String.valueOf(this.item[16]));
			label87.setText(String.valueOf(this.item[17]));
			label88.setText(String.valueOf(this.item[18]));
			label89.setText(String.valueOf(this.item[19]));
			label90.setText(String.valueOf(this.item[20]));
			label91.setText(String.valueOf(this.item[21]));
			this.validate();
		}
		public void paintTake(int a){
			if(a==1){
				label41.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==2){
				label42.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==3){
				label43.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==4){
				label44.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==5){
				label45.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==6){
				label46.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==7){
				label47.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==8){
				label48.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==9){
				label49.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==10){
				label50.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==11){
				label51.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==12){
				label52.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==13){
				label53.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==14){
				label54.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==15){
				label55.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==16){
				label56.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==17){
				label57.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==18){
				label58.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==19){
				label59.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==20){
				label60.setText("持っていく");
				this.takeItem[a]=1;
			}
			if(a==21){
				label61.setText("持っていく");
				this.takeItem[a]=1;
			}
			this.validate();
		}
		public void paintEntake(int a){
			if(a==1){
				label41.setText("");
				this.takeItem[a]=0;
			}
			if(a==2){
				label42.setText("");
				this.takeItem[a]=0;
			}
			if(a==3){
				label43.setText("");
				this.takeItem[a]=0;
			}
			if(a==4){
				label44.setText("");
				this.takeItem[a]=0;
			}
			if(a==5){
				label45.setText("");
				this.takeItem[a]=0;
			}
			if(a==6){
				label46.setText("");
				this.takeItem[a]=0;
			}
			if(a==7){
				label47.setText("");
				this.takeItem[a]=0;
			}
			if(a==8){
				label48.setText("");
				this.takeItem[a]=0;
			}
			if(a==9){
				label49.setText("");
				this.takeItem[a]=0;
			}
			if(a==10){
				label50.setText("");
				this.takeItem[a]=0;
			}
			if(a==11){
				label51.setText("");
				this.takeItem[a]=0;
			}
			if(a==12){
				label52.setText("");
				this.takeItem[a]=0;
			}
			if(a==13){
				label53.setText("");
				this.takeItem[a]=0;
			}
			if(a==14){
				label54.setText("");
				this.takeItem[a]=0;
			}
			if(a==15){
				label55.setText("");
				this.takeItem[a]=0;
			}
			if(a==16){
				label56.setText("");
				this.takeItem[a]=0;
			}
			if(a==17){
				label57.setText("");
				this.takeItem[a]=0;
			}
			if(a==18){
				label58.setText("");
				this.takeItem[a]=0;
			}
			if(a==19){
				label59.setText("");
				this.takeItem[a]=0;
			}
			if(a==20){
				label60.setText("");
				this.takeItem[a]=0;
			}
			if(a==21){
				label61.setText("");
				this.takeItem[a]=0;
			}
			this.validate();
		}
		public void setItem(int i,int a){
			this.item[i]=a;
		}
		public int[] getItem(){
			return item;
		}
		public void setIcon(int a){
			this.item[0]=a;
		}
		public void change(int a){
			if(a==1){
				label10.setIcon(icon1);
			}
			if(a==2){
				label10.setIcon(icon2);
			}
			if(a==3){
				label10.setIcon(icon3);
			}
			if(a==4){
				label10.setIcon(icon4);
			}
			if(a==5){
				label10.setIcon(icon5);
			}
			if(a==6){
				label10.setIcon(icon6);
			}
			if(a==7){
				label10.setIcon(icon7);
			}
			if(a==8){
				label10.setIcon(icon8);
			}
			if(a==9){
				label10.setIcon(icon9);
			}
			if(a==10){
				label10.setIcon(icon10);
			}
			this.validate();
		}
		public int checkCost(){
			if(this.cost<=10){
				return 1;
			}
			else{
				return 0;
			}
		}
		public void setName(String a){
			this.name=a;
			label7.setText(name);
			this.validate();
		}
		public void setMoney(int a){
			this.money=a;
			String b=String.valueOf(money);
			label8.setText(b);
			this.validate();
		}

	}

	public void paintIconCanvas(){
		layout.show(container,"icon");
	}

	public class IconPanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;
		JLabel label9;
		JLabel label10;
		JLabel label11;

		int iconNum;
		int[] item = new int[22];
		public IconPanel(){
			setLayout(null);
			changeButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			changeButton.setBounds(150, 580, 100,40);
			changeButton.setOpaque(false);
			//this.add(changeButton);
			backManagementButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			backManagementButton.setBounds(300, 580, 100,40);
			backManagementButton.setOpaque(false);
			this.add(backManagementButton);

			label1 = new JLabel(icon1);
			label1.setBounds(63, 40, 150, 150);
			this.add(label1);
			label2 = new JLabel(icon2);
			label2.setBounds(243, 40, 150, 150);
			this.add(label2);
			label3 = new JLabel(icon3);
			label3.setBounds(423, 40, 150, 150);
			this.add(label3);
			label4 = new JLabel(icon4);
			label4.setBounds(603, 40, 150, 150);
			this.add(label4);
			label5 = new JLabel(icon5);
			label5.setBounds(783, 40, 150, 150);
			this.add(label5);
			label6 = new JLabel(icon6);
			label6.setBounds(63, 240, 150, 150);
			this.add(label6);
			label7 = new JLabel(icon7);
			label7.setBounds(243, 240, 150, 150);
			this.add(label7);
			label8 = new JLabel(icon8);
			label8.setBounds(423, 240, 150, 150);
			this.add(label8);
			label9 = new JLabel(icon9);
			label9.setBounds(603, 240, 150, 150);
			this.add(label9);
			label10 = new JLabel(icon10);
			label10.setBounds(783, 240, 150, 150);
			this.add(label10);

			icon1Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon1Button.setBounds(63, 40, 150, 150);
			icon1Button.setOpaque(false);
			this.add(icon1Button);
			icon2Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon2Button.setBounds(243, 40, 150, 150);
			icon2Button.setOpaque(false);
			this.add(icon2Button);
			icon3Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon3Button.setBounds(423, 40, 150, 150);
			icon3Button.setOpaque(false);
			this.add(icon3Button);
			icon4Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon4Button.setBounds(603, 40, 150, 150);
			icon4Button.setOpaque(false);
			this.add(icon4Button);
			icon5Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon5Button.setBounds(783, 40, 150, 150);
			icon5Button.setOpaque(false);
			this.add(icon5Button);
			icon6Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon6Button.setBounds(63, 240, 150, 150);
			icon6Button.setOpaque(false);
			this.add(icon6Button);
			icon7Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon7Button.setBounds(243, 240, 150, 150);
			icon7Button.setOpaque(false);
			this.add(icon7Button);
			icon8Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon8Button.setBounds(423, 240, 150, 150);
			icon8Button.setOpaque(false);
			this.add(icon8Button);
			icon9Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon9Button.setBounds(603, 240, 150, 150);
			icon9Button.setOpaque(false);
			this.add(icon9Button);
			icon10Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			icon10Button.setBounds(783, 240, 150, 150);
			icon10Button.setOpaque(false);
			this.add(icon10Button);
			label11 = new JLabel("アイコンをクリックしてください");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label11.setBounds(450, 580, 500, 30);
			this.add(label11);
			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);
		}
		public void setIcon(int a){
			this.iconNum=a;
		}
		public int getIcon(){
			return this.iconNum;
		}
		public void setItem(int[] i){
			this.item=i;
		}
		public int getItem(int a){
			return item[a];
		}
	}

	public void paintShopCanvas(){
		layout.show(container,"shop");
	}

	public class ShopPanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		int money=0;
		int pageNum=1;
		int[] item = new int[22];
		public ShopPanel(){
			setLayout(null);
			label8=new JLabel(String.valueOf(money));
			label8.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label8.setHorizontalAlignment(JLabel.RIGHT);
			label8.setBounds(650, 500,250, 30);
			this.add(label8);

			label1 = new JLabel("所持金                 円");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label1.setOpaque(true);
			label1.setBackground(new Color(0,255,153));
			label1.setBounds(700, 500,250, 30);
			this.add(label1);



			label11 = new JLabel(tsuzu);
			label11.setBounds(50, 30, 183, 242);
			this.add(label11);
			label12 = new JLabel(tsuitou);
			label12.setBounds(263, 30, 183, 242);
			this.add(label12);
			label13 = new JLabel(tsanso);
			label13.setBounds(473, 30, 183, 242);
			this.add(label13);
			label14 = new JLabel(trantan);
			label14.setBounds(683, 30,183, 242);
			this.add(label14);
			label15 = new JLabel(trainwear);
			label15.setBounds(50, 280, 183, 242);
			this.add(label15);
			label16 = new JLabel(tonigiri);
			label16.setBounds(263, 280, 183, 242);
			this.add(label16);
			label17 = new JLabel(tomamori1);
			label17.setBounds(473, 280, 183, 242);
			this.add(label17);

			shop1Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop1Button.setBounds(50, 30, 183, 242);
			shop1Button.setOpaque(false);
			this.add(shop1Button);
			shop2Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop2Button.setBounds(263, 30, 183, 242);
			shop2Button.setOpaque(false);
			this.add(shop2Button);
			shop3Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop3Button.setBounds(473, 30, 183, 242);
			shop3Button.setOpaque(false);
			this.add(shop3Button);
			shop4Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop4Button.setBounds(683, 30,183, 242);
			shop4Button.setOpaque(false);
			this.add(shop4Button);
			shop5Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop5Button.setBounds(50, 280, 183, 242);
			shop5Button.setOpaque(false);
			this.add(shop5Button);
			shop6Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop6Button.setBounds(263, 280, 183, 242);
			shop6Button.setOpaque(false);
			this.add(shop6Button);
			shop7Button.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			shop7Button.setBounds(473, 280, 183, 242);
			shop7Button.setOpaque(false);
			this.add(shop7Button);

			menuButton6.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			menuButton6.setBounds(720, 570, 150,50);
			this.add(menuButton6);

			nextButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			nextButton.setBounds(500, 570, 150,50);
			this.add(nextButton);

			label0 = new JLabel(bgShop);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);

		}
		public void setItem(int i,int a){
			this.item[i]=a;int[] item = new int[22];
		}
		public void paintPanel1(){
			label11.setIcon(tsuzu);
			label12.setIcon(tsuitou);
			label13.setIcon(tsanso);
			label14.setIcon(trantan);
			label15.setIcon(trainwear);
			label16.setIcon(tonigiri);
			label17.setIcon(tomamori1);
			this.validate();
		}
		public void paintPanel2(){
			label11.setIcon(tomamori2);
			label12.setIcon(tnebukuro);
			label13.setIcon(tmiso);
			label14.setIcon(tmedicine);
			label15.setIcon(tlump);
			label16.setIcon(tkutu);
			label17.setIcon(tkairo);
			this.validate();
		}
		public void paintPanel3(){
			label11.setIcon(tdrink);
			label12.setIcon(tcoffee);
			label13.setIcon(tcd);
			label14.setIcon(tcap);
			label15.setIcon(tairbag);
			label16.setIcon(tboukan);
			label17.setIcon(taizen);
			this.validate();
		}
		public void getItem1(){

			if(pageNum==1){
				if(this.money>=bell.getValue()){
					item[1]++;
					this.money=this.money-bell.getValue();

				}
			}
			if(pageNum==2){
				if(this.money>=omamori1.getValue()){
					item[8]++;
					this.money=this.money-omamori1.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=drink.getValue()){
					item[15]++;
					this.money=this.money-drink.getValue();				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void getItem2(){
			if(pageNum==1){
				if(this.money>=waterBottole.getValue()){
					item[2]++;
					this.money=this.money-waterBottole.getValue();
				}
			}
			if(pageNum==2){
				if(this.money>=sleepingBag.getValue()){
					item[9]++;
					this.money=this.money-sleepingBag.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=coffeeMaker.getValue()){
					item[16]++;
					this.money=this.money-coffeeMaker.getValue();
				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void getItem3(){
			if(pageNum==1){
				if(this.money>=Oxygentank.getValue()){
					item[3]++;
					this.money=this.money-Oxygentank.getValue();
				}
			}
			if(pageNum==2){
				if(this.money>=misoSoup.getValue()){
					item[10]++;
					this.money=this.money-misoSoup.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=miharukasu.getValue()){
					item[17]++;
					this.money=this.money-miharukasu.getValue();
				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void getItem4(){
			if(pageNum==1){
				if(this.money>=rantan.getValue()){
					item[4]++;
					this.money=this.money-rantan.getValue();
				}
			}
			if(pageNum==2){
				if(this.money>=medicine.getValue()){
					item[11]++;
					this.money=this.money-medicine.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=cap.getValue()){
					item[18]++;
					this.money=this.money-cap.getValue();
				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void getItem5(){
			if(pageNum==1){
				if(this.money>=reinCoat.getValue()){
					item[5]++;
					this.money=this.money-reinCoat.getValue();
				}
			}
			if(pageNum==2){
				if(this.money>=headLump.getValue()){
					item[12]++;
					this.money=this.money-headLump.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=airBag.getValue()){
					item[19]++;
					this.money=this.money-airBag.getValue();
				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void getItem6(){
			if(pageNum==1){
				if(this.money>=onigiri.getValue()){
					item[6]++;
					this.money=this.money-onigiri.getValue();
				}
			}
			if(pageNum==2){
				if(this.money>=climbShoes.getValue()){
					item[13]++;
					this.money=this.money-climbShoes.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=againstColdWear.getValue()){
					item[20]++;
					this.money=this.money-againstColdWear.getValue();
				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void getItem7(){
			if(pageNum==1){
				if(this.money>=omamori2.getValue()){
					item[7]++;
					this.money=this.money-omamori2.getValue();
				}
			}
			if(pageNum==2){
				if(this.money>=kairo.getValue()){
					item[14]++;
					this.money=this.money-kairo.getValue();
				}
			}
			if(pageNum==3){
				if(this.money>=aizen.getValue()){
					item[21]++;
					this.money=this.money-aizen.getValue();
				}
			}
			label8.setText(String.valueOf(this.money));
			this.validate();
		}
		public void changePageNum(){
			if(pageNum!=3){
				pageNum=pageNum+1;
			}
			else{
				pageNum=1;
			}
			this.validate();
		}
		public int getPageNum(){
			return pageNum;
		}
		public void setMoney(int a){
			this.money=a;
			label8.setText(String.valueOf(a));
			this.validate();
		}
		public int[] getItem(){
			return item;
		}
		public int getMoney(){
			return this.money;
		}
	}
	public void paintFStageCanvas(){
		layout.show(container,"fstage");
	}

	public class FStagePanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		JLabel label18;
		JLabel label19;
		JLabel label20;
		JLabel label21;
		JLabel label22;
		JLabel label23;
		JLabel label24;
		JLabel label25;
		JLabel label26;
		JLabel label27;
		JLabel label28;
		JLabel label29;

		JLabel label30;
		JLabel label31;
		JLabel label32;
		JLabel label33;
		JLabel label34;
		JLabel label35;
		JLabel label36;
		JLabel label37;
		JLabel label38;
		JLabel label39;


		int rankNum=0;

		public FStagePanel(){
			setLayout(null);

			label1 = new JLabel("富士山");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label1.setOpaque(true);
			label1.setBackground(new Color(0,255,153));
			label1.setBounds(700, 50, 83, 30);
			this.add(label1);

			label2 = new JLabel("ハイスコア");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label2.setOpaque(false);
			label2.setBounds(630, 260, 183, 30);
			this.add(label2);

			label10 = new JLabel("1位");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label10.setBounds(630, 290, 183, 30);
			this.add(label10);
			label11 = new JLabel("2位");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label11.setBounds(630, 310, 183, 30);
			this.add(label11);
			label12= new JLabel("3位");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label12.setBounds(630, 330, 183, 30);
			this.add(label12);
			label13 = new JLabel("4位");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label13.setBounds(630, 350, 183, 30);
			this.add(label13);
			label14 = new JLabel("5位");
			label14.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label14.setBounds(630, 370, 183, 30);
			this.add(label14);
			label15 = new JLabel("6位");
			label15.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label15.setBounds(630, 390, 183, 30);
			this.add(label15);
			label16 = new JLabel("7位");
			label16.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label16.setBounds(630, 410, 183, 30);
			this.add(label16);
			label17 = new JLabel("8位");
			label17.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label17.setBounds(630, 430, 183, 30);
			this.add(label17);
			label18 = new JLabel("9位");
			label18.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label18.setBounds(630, 450, 183, 30);
			this.add(label18);
			label19 = new JLabel("10位");
			label19.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label19.setBounds(630, 470, 183, 30);
			this.add(label19);
			label20 = new JLabel("");
			label20.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label20.setHorizontalAlignment(JLabel.LEFT);
			label20.setBounds(700, 290, 183, 30);
			this.add(label20);
			label21 = new JLabel("");
			label21.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label21.setHorizontalAlignment(JLabel.LEFT);
			label21.setBounds(700, 310, 183, 30);
			this.add(label21);
			label22= new JLabel("");
			label22.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label22.setHorizontalAlignment(JLabel.LEFT);
			label22.setBounds(700, 330, 183, 30);
			this.add(label22);
			label23 = new JLabel("");
			label23.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label23.setHorizontalAlignment(JLabel.LEFT);
			label23.setBounds(700, 350, 183, 30);
			this.add(label23);
			label24 = new JLabel("");
			label24.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label24.setHorizontalAlignment(JLabel.LEFT);
			label24.setBounds(700, 370, 183, 30);
			this.add(label24);
			label25 = new JLabel("");
			label25.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label25.setHorizontalAlignment(JLabel.LEFT);
			label25.setBounds(700, 390, 183, 30);
			this.add(label25);
			label26 = new JLabel("");
			label26.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label26.setHorizontalAlignment(JLabel.LEFT);
			label26.setBounds(700, 410, 183, 30);
			this.add(label26);
			label27 = new JLabel("");
			label27.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label27.setHorizontalAlignment(JLabel.LEFT);
			label27.setBounds(700, 430, 183, 30);
			this.add(label27);
			label28 = new JLabel("");
			label28.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label28.setHorizontalAlignment(JLabel.LEFT);
			label28.setBounds(700, 450, 183, 30);
			this.add(label28);
			label29 = new JLabel("");
			label29.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label29.setHorizontalAlignment(JLabel.LEFT);
			label29.setBounds(700, 470, 183, 30);
			this.add(label29);

			label30 = new JLabel("");
			label30.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label30.setHorizontalAlignment(JLabel.LEFT);
			label30.setBounds(800, 290, 183, 30);
			this.add(label30);
			label31 = new JLabel("");
			label31.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label31.setHorizontalAlignment(JLabel.LEFT);
			label31.setBounds(800, 310, 183, 30);
			this.add(label31);
			label32= new JLabel("");
			label32.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label32.setHorizontalAlignment(JLabel.LEFT);
			label32.setBounds(800, 330, 183, 30);
			this.add(label32);
			label33 = new JLabel("");
			label33.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label33.setHorizontalAlignment(JLabel.LEFT);
			label33.setBounds(800, 350, 183, 30);
			this.add(label33);
			label34 = new JLabel("");
			label34.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label34.setHorizontalAlignment(JLabel.LEFT);
			label34.setBounds(800, 370, 183, 30);
			this.add(label34);
			label35 = new JLabel("");
			label35.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label35.setHorizontalAlignment(JLabel.LEFT);
			label35.setBounds(800, 390, 183, 30);
			this.add(label35);
			label36 = new JLabel("");
			label36.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label36.setHorizontalAlignment(JLabel.LEFT);
			label36.setBounds(800, 410, 183, 30);
			this.add(label36);
			label37 = new JLabel("");
			label37.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label37.setHorizontalAlignment(JLabel.LEFT);
			label37.setBounds(800, 430, 183, 30);
			this.add(label37);
			label38 = new JLabel("");
			label38.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label38.setHorizontalAlignment(JLabel.LEFT);
			label38.setBounds(800, 450, 183, 30);
			this.add(label38);
			label39 = new JLabel("");
			label39.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label39.setHorizontalAlignment(JLabel.LEFT);
			label39.setBounds(800, 470, 183, 30);
			this.add(label39);



			label3 = new JLabel(mtFuzi);
			label3.setBounds(50, 70, 179,120);
			this.add(label3);

			label4 = new JLabel(bgStageRank);
			label4.setBounds(580,265,382,254);
			this.add(label4);

			label5 = new JLabel(bgText2);
			label5.setBounds(580,93,358,168);
			//this.add(label5);

			label6 = new JLabel(detailFuji);
			label6.setBounds(50, 200, 400,400);
			this.add(label6);

			menuButton1.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			menuButton1.setForeground(Color.BLACK);
			menuButton1.setBounds(680, 500, 250, 50);
			menuButton1.setBackground(Color.WHITE);
			this.add(menuButton1);

			startButton1.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			startButton1.setForeground(Color.BLACK);
			startButton1.setBounds(680, 560, 250, 50);
			startButton1.setBackground(Color.WHITE);
			this.add(startButton1);


			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);



		}
		public void paintRank(String name,int score){
			int day=score/24;
			int hour=score%24;

			if(rankNum==0){
				label20.setText(name);
				label30.setText(day+"日"+hour+"時間");
			}
			if(rankNum==1){
				label21.setText(name);
				label31.setText(day+"日"+hour+"時間");
			}
			if(rankNum==2){
				label22.setText(name);
				label32.setText(day+"日"+hour+"時間");
			}
			if(rankNum==3){
				label23.setText(name);
				label33.setText(day+"日"+hour+"時間");
			}
			if(rankNum==4){
				label24.setText(name);
				label34.setText(day+"日"+hour+"時間");
			}
			if(rankNum==5){
				label25.setText(name);
				label35.setText(day+"日"+hour+"時間");
			}
			if(rankNum==6){
				label26.setText(name);
				label36.setText(day+"日"+hour+"時間");
			}
			if(rankNum==7){
				label27.setText(name);
				label37.setText(day+"日"+hour+"時間");
			}
			if(rankNum==8){
				label28.setText(name);
				label38.setText(day+"日"+hour+"時間");
			}
			if(rankNum==9){
				label29.setText(name);
				label39.setText(day+"日"+hour+"時間");
			}
			this.validate();
			rankNum++;
		}
	}

	public void paintEStageCanvas(){
		layout.show(container,"estage");
	}

	public class EStagePanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;

		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		JLabel label18;
		JLabel label19;
		JLabel label20;
		JLabel label21;
		JLabel label22;
		JLabel label23;
		JLabel label24;
		JLabel label25;
		JLabel label26;
		JLabel label27;
		JLabel label28;
		JLabel label29;

		JLabel label30;
		JLabel label31;
		JLabel label32;
		JLabel label33;
		JLabel label34;
		JLabel label35;
		JLabel label36;
		JLabel label37;
		JLabel label38;
		JLabel label39;

		int rankNum=0;
		public EStagePanel(){
			setLayout(null);

			label1 = new JLabel("エベレスト");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label1.setOpaque(true);
			label1.setBackground(new Color(0,255,153));
			label1.setBounds(700, 50, 100, 30);
			this.add(label1);

			label2 = new JLabel("ハイスコア");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label2.setOpaque(false);
			label2.setBounds(630, 273, 183, 30);
			this.add(label2);

			label10 = new JLabel("1位");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label10.setBounds(630, 290, 183, 30);
			this.add(label10);
			label11 = new JLabel("2位");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label11.setBounds(630, 310, 183, 30);
			this.add(label11);
			label12= new JLabel("3位");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label12.setBounds(630, 330, 183, 30);
			this.add(label12);
			label13 = new JLabel("4位");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label13.setBounds(630, 350, 183, 30);
			this.add(label13);
			label14 = new JLabel("5位");
			label14.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label14.setBounds(630, 370, 183, 30);
			this.add(label14);
			label15 = new JLabel("6位");
			label15.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label15.setBounds(630, 390, 183, 30);
			this.add(label15);
			label16 = new JLabel("7位");
			label16.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label16.setBounds(630, 410, 183, 30);
			this.add(label16);
			label17 = new JLabel("8位");
			label17.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label17.setBounds(630, 430, 183, 30);
			this.add(label17);
			label18 = new JLabel("9位");
			label18.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label18.setBounds(630, 450, 183, 30);
			this.add(label18);
			label19 = new JLabel("10位");
			label19.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label19.setBounds(630, 470, 183, 30);
			this.add(label19);

			label20 = new JLabel("");
			label20.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label20.setHorizontalAlignment(JLabel.LEFT);
			label20.setBounds(700, 290, 183, 30);
			this.add(label20);
			label21 = new JLabel("");
			label21.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label21.setHorizontalAlignment(JLabel.LEFT);
			label21.setBounds(700, 310, 183, 30);
			this.add(label21);
			label22= new JLabel("");
			label22.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label22.setHorizontalAlignment(JLabel.LEFT);
			label22.setBounds(700, 330, 183, 30);
			this.add(label22);
			label23 = new JLabel("");
			label23.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label23.setHorizontalAlignment(JLabel.LEFT);
			label23.setBounds(700, 350, 183, 30);
			this.add(label23);
			label24 = new JLabel("");
			label24.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label24.setHorizontalAlignment(JLabel.LEFT);
			label24.setBounds(700, 370, 183, 30);
			this.add(label24);
			label25 = new JLabel("");
			label25.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label25.setHorizontalAlignment(JLabel.LEFT);
			label25.setBounds(700, 390, 183, 30);
			this.add(label25);
			label26 = new JLabel("");
			label26.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label26.setHorizontalAlignment(JLabel.LEFT);
			label26.setBounds(700, 410, 183, 30);
			this.add(label26);
			label27 = new JLabel("");
			label27.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label27.setHorizontalAlignment(JLabel.LEFT);
			label27.setBounds(700, 430, 183, 30);
			this.add(label27);
			label28 = new JLabel("");
			label28.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label28.setHorizontalAlignment(JLabel.LEFT);
			label28.setBounds(700, 450, 183, 30);
			this.add(label28);
			label29 = new JLabel("");
			label29.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label29.setHorizontalAlignment(JLabel.LEFT);
			label29.setBounds(700, 470, 183, 30);
			this.add(label29);

			label30 = new JLabel("");
			label30.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label30.setHorizontalAlignment(JLabel.LEFT);
			label30.setBounds(800, 290, 183, 30);
			this.add(label30);
			label31 = new JLabel("");
			label31.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label31.setHorizontalAlignment(JLabel.LEFT);
			label31.setBounds(800, 310, 183, 30);
			this.add(label31);
			label32= new JLabel("");
			label32.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label32.setHorizontalAlignment(JLabel.LEFT);
			label32.setBounds(800, 330, 183, 30);
			this.add(label32);
			label33 = new JLabel("");
			label33.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label33.setHorizontalAlignment(JLabel.LEFT);
			label33.setBounds(800, 350, 183, 30);
			this.add(label33);
			label34 = new JLabel("");
			label34.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label34.setHorizontalAlignment(JLabel.LEFT);
			label34.setBounds(800, 370, 183, 30);
			this.add(label34);
			label35 = new JLabel("");
			label35.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label35.setHorizontalAlignment(JLabel.LEFT);
			label35.setBounds(800, 390, 183, 30);
			this.add(label35);
			label36 = new JLabel("");
			label36.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label36.setHorizontalAlignment(JLabel.LEFT);
			label36.setBounds(800, 410, 183, 30);
			this.add(label36);
			label37 = new JLabel("");
			label37.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label37.setHorizontalAlignment(JLabel.LEFT);
			label37.setBounds(800, 430, 183, 30);
			this.add(label37);
			label38 = new JLabel("");
			label38.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label38.setHorizontalAlignment(JLabel.LEFT);
			label38.setBounds(800, 450, 183, 30);
			this.add(label38);
			label39 = new JLabel("");
			label39.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label39.setHorizontalAlignment(JLabel.LEFT);
			label39.setBounds(800, 470, 183, 30);
			this.add(label39);

			label3 = new JLabel(mtEv);
			label3.setBounds(50, 70, 179,120);
			this.add(label3);

			label4 = new JLabel(bgStageRank);
			label4.setBounds(580,265,382,254);
			this.add(label4);

			label5 = new JLabel(bgText2);
			label5.setBounds(580,93,358,168);
			//this.add(label5);

			label6 = new JLabel(detailEv);
			label6.setBounds(50, 200, 400,400);
			this.add(label6);

			menuButton2.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			menuButton2.setForeground(Color.BLACK);
			menuButton2.setBounds(680, 500, 250, 50);
			menuButton2.setBackground(Color.WHITE);
			this.add(menuButton2);

			startButton2.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			startButton2.setForeground(Color.BLACK);
			startButton2.setBounds(680, 560, 250, 50);
			startButton2.setBackground(Color.WHITE);
			this.add(startButton2);



			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);
		}
		public void paintRank(String name,int score){
			int day=score/24;
			int hour=score%24;

			if(rankNum==0){
				label20.setText(name);
				label30.setText(day+"日"+hour+"時間");
			}
			if(rankNum==1){
				label21.setText(name);
				label31.setText(day+"日"+hour+"時間");
			}
			if(rankNum==2){
				label22.setText(name);
				label32.setText(day+"日"+hour+"時間");
			}
			if(rankNum==3){
				label23.setText(name);
				label33.setText(day+"日"+hour+"時間");
			}
			if(rankNum==4){
				label24.setText(name);
				label34.setText(day+"日"+hour+"時間");
			}
			if(rankNum==5){
				label25.setText(name);
				label35.setText(day+"日"+hour+"時間");
			}
			if(rankNum==6){
				label26.setText(name);
				label36.setText(day+"日"+hour+"時間");
			}
			if(rankNum==7){
				label27.setText(name);
				label37.setText(day+"日"+hour+"時間");
			}
			if(rankNum==8){
				label28.setText(name);
				label38.setText(day+"日"+hour+"時間");
			}
			if(rankNum==9){
				label29.setText(name);
				label39.setText(day+"日"+hour+"時間");
			}
			this.validate();
			rankNum++;
		}
	}
	public void paintMStageCanvas(){
		layout.show(container,"mstage");
	}

	public class MStagePanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;

		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		JLabel label18;
		JLabel label19;
		JLabel label20;
		JLabel label21;
		JLabel label22;
		JLabel label23;
		JLabel label24;
		JLabel label25;
		JLabel label26;
		JLabel label27;
		JLabel label28;
		JLabel label29;
		JLabel label30;
		JLabel label31;
		JLabel label32;
		JLabel label33;
		JLabel label34;
		JLabel label35;
		JLabel label36;
		JLabel label37;
		JLabel label38;
		JLabel label39;

		int rankNum=0;
		public MStagePanel(){
			setLayout(null);


			label1 = new JLabel("ヴィンソン・マシフ");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label1.setOpaque(true);
			label1.setBackground(new Color(0,255,153));
			label1.setBounds(700, 50,175, 30);
			this.add(label1);

			label2 = new JLabel("ハイスコア");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label2.setOpaque(false);
			label2.setBounds(630, 273, 183, 30);
			this.add(label2);

			label10 = new JLabel("1位");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label10.setBounds(630, 290, 183, 30);
			this.add(label10);
			label11 = new JLabel("2位");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label11.setBounds(630, 310, 183, 30);
			this.add(label11);
			label12= new JLabel("3位");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label12.setBounds(630, 330, 183, 30);
			this.add(label12);
			label13 = new JLabel("4位");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label13.setBounds(630, 350, 183, 30);
			this.add(label13);
			label14 = new JLabel("5位");
			label14.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label14.setBounds(630, 370, 183, 30);
			this.add(label14);
			label15 = new JLabel("6位");
			label15.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label15.setBounds(630, 390, 183, 30);
			this.add(label15);
			label16 = new JLabel("7位");
			label16.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label16.setBounds(630, 410, 183, 30);
			this.add(label16);
			label17 = new JLabel("8位");
			label17.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label17.setBounds(630, 430, 183, 30);
			this.add(label17);
			label18 = new JLabel("9位");
			label18.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label18.setBounds(630, 450, 183, 30);
			this.add(label18);
			label19 = new JLabel("10位");
			label19.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label19.setBounds(630, 470, 183, 30);
			this.add(label19);

			label20 = new JLabel("");
			label20.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label20.setHorizontalAlignment(JLabel.LEFT);
			label20.setBounds(700, 290, 183, 30);
			this.add(label20);
			label21 = new JLabel("");
			label21.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label21.setHorizontalAlignment(JLabel.LEFT);
			label21.setBounds(700, 310, 183, 30);
			this.add(label21);
			label22= new JLabel("");
			label22.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label22.setHorizontalAlignment(JLabel.LEFT);
			label22.setBounds(700, 330, 183, 30);
			this.add(label22);
			label23 = new JLabel("");
			label23.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label23.setHorizontalAlignment(JLabel.LEFT);
			label23.setBounds(700, 350, 183, 30);
			this.add(label23);
			label24 = new JLabel("");
			label24.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label24.setHorizontalAlignment(JLabel.LEFT);
			label24.setBounds(700, 370, 183, 30);
			this.add(label24);
			label25 = new JLabel("");
			label25.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label25.setHorizontalAlignment(JLabel.LEFT);
			label25.setBounds(700, 390, 183, 30);
			this.add(label25);
			label26 = new JLabel("");
			label26.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label26.setHorizontalAlignment(JLabel.LEFT);
			label26.setBounds(700, 410, 183, 30);
			this.add(label26);
			label27 = new JLabel("");
			label27.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label27.setHorizontalAlignment(JLabel.LEFT);
			label27.setBounds(700, 430, 183, 30);
			this.add(label27);
			label28 = new JLabel("");
			label28.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label28.setHorizontalAlignment(JLabel.LEFT);
			label28.setBounds(700, 450, 183, 30);
			this.add(label28);
			label29 = new JLabel("");
			label29.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label29.setHorizontalAlignment(JLabel.LEFT);
			label29.setBounds(700, 470, 183, 30);
			this.add(label29);

			label30 = new JLabel("");
			label30.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label30.setHorizontalAlignment(JLabel.LEFT);
			label30.setBounds(800, 290, 183, 30);
			this.add(label30);
			label31 = new JLabel("");
			label31.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label31.setHorizontalAlignment(JLabel.LEFT);
			label31.setBounds(800, 310, 183, 30);
			this.add(label31);
			label32= new JLabel("");
			label32.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label32.setHorizontalAlignment(JLabel.LEFT);
			label32.setBounds(800, 330, 183, 30);
			this.add(label32);
			label33 = new JLabel("");
			label33.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label33.setHorizontalAlignment(JLabel.LEFT);
			label33.setBounds(800, 350, 183, 30);
			this.add(label33);
			label34 = new JLabel("");
			label34.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label34.setHorizontalAlignment(JLabel.LEFT);
			label34.setBounds(800, 370, 183, 30);
			this.add(label34);
			label35 = new JLabel("");
			label35.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label35.setHorizontalAlignment(JLabel.LEFT);
			label35.setBounds(800, 390, 183, 30);
			this.add(label35);
			label36 = new JLabel("");
			label36.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label36.setHorizontalAlignment(JLabel.LEFT);
			label36.setBounds(800, 410, 183, 30);
			this.add(label36);
			label37 = new JLabel("");
			label37.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label37.setHorizontalAlignment(JLabel.LEFT);
			label37.setBounds(800, 430, 183, 30);
			this.add(label37);
			label38 = new JLabel("");
			label38.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label38.setHorizontalAlignment(JLabel.LEFT);
			label38.setBounds(800, 450, 183, 30);
			this.add(label38);
			label39 = new JLabel("");
			label39.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label39.setHorizontalAlignment(JLabel.LEFT);
			label39.setBounds(800, 470, 183, 30);
			this.add(label39);

			label3 = new JLabel(mtMa);
			label3.setBounds(50, 70, 179,120);
			this.add(label3);

			label4 = new JLabel(bgStageRank);
			label4.setBounds(580,265,382,254);
			this.add(label4);

			label5 = new JLabel(bgText2);
			label5.setBounds(580,93,358,168);
			//this.add(label5);

			label6 = new JLabel(detailMa);
			label6.setBounds(50, 200, 400,400);
			this.add(label6);

			menuButton3.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			menuButton3.setForeground(Color.BLACK);
			menuButton3.setBounds(680, 500, 250, 50);
			menuButton3.setBackground(Color.WHITE);
			this.add(menuButton3);

			startButton3.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			startButton3.setForeground(Color.BLACK);
			startButton3.setBounds(680, 560, 250, 50);
			startButton3.setBackground(Color.WHITE);
			this.add(startButton3);

			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);

		}
		public void paintRank(String name,int score){
			int day=score/24;
			int hour=score%24;

			if(rankNum==0){
				label20.setText(name);
				label30.setText(day+"日"+hour+"時間");
			}
			if(rankNum==1){
				label21.setText(name);
				label31.setText(day+"日"+hour+"時間");
			}
			if(rankNum==2){
				label22.setText(name);
				label32.setText(day+"日"+hour+"時間");
			}
			if(rankNum==3){
				label23.setText(name);
				label33.setText(day+"日"+hour+"時間");
			}
			if(rankNum==4){
				label24.setText(name);
				label34.setText(day+"日"+hour+"時間");
			}
			if(rankNum==5){
				label25.setText(name);
				label35.setText(day+"日"+hour+"時間");
			}
			if(rankNum==6){
				label26.setText(name);
				label36.setText(day+"日"+hour+"時間");
			}
			if(rankNum==7){
				label27.setText(name);
				label37.setText(day+"日"+hour+"時間");
			}
			if(rankNum==8){
				label28.setText(name);
				label38.setText(day+"日"+hour+"時間");
			}
			if(rankNum==9){
				label29.setText(name);
				label39.setText(day+"日"+hour+"時間");
			}
			this.validate();
			rankNum++;
		}
	}
	public void paintGameCanvas(){
		layout.show(container,"game");
	}

	public class GamePanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;
		JLabel label9;
		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label20;
		JLabel label21;
		JLabel label22;
		JLabel label23;
		JLabel label24;
		Mountain mountain;
		//ゲームログの表示
		JTextField gameLog1=new JTextField();
		String log1=null;
		//経過時間
		int time=0;
		String day="0",hour="0";
		//四つの数字
		int dice[]=new int[4];
		int eventNumber=-1;
		//選択肢
		int selectNum[][]=new int[6][2];
		public GamePanel(Mountain mountain){
			setLayout(null);

			//テキスト文の初期化
			log1="登山開始";
			this.mountain=mountain;

			label1 = new JLabel("経過時間");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 35));
			label1.setOpaque(false);
			label1.setBounds(780, 80, 183, 60);
			this.add(label1);

			label2 = new JLabel("  日    時間");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 35));
			label2.setOpaque(false);
			label2.setBounds(780, 180, 283, 40);
			this.add(label2);
			//何日立ったか
			label12 = new JLabel(day);
			label12.setHorizontalAlignment(JLabel.RIGHT);
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 35));
			label12.setOpaque(false);
			label12.setBounds(730, 180, 70, 40);
			this.add(label12);
			//何時間経ったか
			label13 = new JLabel(hour);
			label13.setHorizontalAlignment(JLabel.RIGHT);
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 35));
			label13.setOpaque(false);
			label13.setBounds(785, 180, 100, 40);
			this.add(label13);

			treeButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			treeButton.setForeground(Color.BLACK);
			treeButton.setBounds(720, 300, 250, 50);
			treeButton.setBackground(Color.WHITE);
			this.add(treeButton);

			ruleButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			ruleButton.setForeground(Color.BLACK);
			ruleButton.setBounds(720, 370, 250, 50);
			ruleButton.setBackground(Color.WHITE);
			this.add(ruleButton);

			itemButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			itemButton.setForeground(Color.BLACK);
			itemButton.setBounds(720, 440, 250, 50);
			itemButton.setBackground(Color.WHITE);
			this.add(itemButton);

			yamabikoButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			yamabikoButton.setForeground(Color.BLACK);
			yamabikoButton.setBounds(720, 510, 250, 50);
			yamabikoButton.setBackground(Color.WHITE);
			this.add(yamabikoButton);

			restButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			restButton.setForeground(Color.BLACK);
			restButton.setBounds(720, 580, 250, 50);
			restButton.setBackground(Color.WHITE);
			this.add(restButton);

			for(int i=0;i<11;i++){
				for(int k=0;k<13;k++){
					boardButton[i][k]=new JButton();
					boardButton[i][k].setBackground(Color.WHITE);
					//名前を付ける
					//boardButton[i][k].setActionCommand(Integer.toString(i));
				}
			}

			boardButton[0][0].setBounds(175,280,15,15);
			boardButton[0][1].setBounds(250,155,15,15);
			boardButton[0][2].setBounds(290,30,15,15);
			boardButton[10][0].setBounds(550,280,15,15);
			boardButton[10][1].setBounds(478,155,15,15);
			boardButton[10][2].setBounds(439,30,15,15);

			boardButton[1][4].setBounds(308,30,15,15);
			boardButton[1][3].setBounds(298,90,15,15);
			boardButton[1][2].setBounds(275,150,15,15);
			boardButton[1][1].setBounds(248,210,15,15);
			boardButton[1][0].setBounds(198,280,15,15);
			boardButton[9][4].setBounds(421,30,15,15);
			boardButton[9][3].setBounds(430,90,15,15);
			boardButton[9][2].setBounds(451,150,15,15);
			boardButton[9][1].setBounds(476,210,15,15);
			boardButton[9][0].setBounds(528,280,15,15);

			boardButton[2][6].setBounds(323,30,15,15);
			boardButton[2][5].setBounds(322,80,15,15);
			boardButton[2][4].setBounds(310,130,15,15);
			boardButton[2][3].setBounds(290,180,15,15);
			boardButton[2][2].setBounds(268,230,15,15);
			boardButton[2][1].setBounds(230,280,15,15);
			boardButton[2][0].setBounds(198,330,15,15);
			boardButton[8][6].setBounds(406,30,15,15);
			boardButton[8][5].setBounds(412,80,15,15);
			boardButton[8][4].setBounds(422,130,15,15);
			boardButton[8][3].setBounds(438,180,15,15);
			boardButton[8][2].setBounds(463,230,15,15);
			boardButton[8][1].setBounds(490,280,15,15);
			boardButton[8][0].setBounds(528,330,15,15);

			boardButton[3][8].setBounds(338,30,15,15);
			boardButton[3][7].setBounds(337,60,15,15);
			boardButton[3][6].setBounds(335,100,15,15);
			boardButton[3][5].setBounds(327,140,15,15);
			boardButton[3][4].setBounds(315,180,15,15);
			boardButton[3][3].setBounds(300,220,15,15);
			boardButton[3][2].setBounds(278,260,15,15);
			boardButton[3][1].setBounds(260,300,15,15);
			boardButton[3][0].setBounds(235,340,15,15);

			boardButton[7][8].setBounds(391,30,15,15);
			boardButton[7][7].setBounds(394,60,15,15);
			boardButton[7][6].setBounds(398,100,15,15);
			boardButton[7][5].setBounds(402,140,15,15);
			boardButton[7][4].setBounds(412,180,15,15);
			boardButton[7][3].setBounds(428,220,15,15);
			boardButton[7][2].setBounds(443,260,15,15);
			boardButton[7][1].setBounds(460,300,15,15);
			boardButton[7][0].setBounds(480,340,15,15);

			boardButton[4][10].setBounds(353,30,15,15);
			boardButton[4][9].setBounds(351,70,15,15);
			boardButton[4][8].setBounds(350,100,15,15);
			boardButton[4][7].setBounds(347,130,15,15);
			boardButton[4][6].setBounds(343,160,15,15);
			boardButton[4][5].setBounds(338,190,15,15);
			boardButton[4][4].setBounds(332,220,15,15);
			boardButton[4][3].setBounds(324,250,15,15);
			boardButton[4][2].setBounds(318,280,15,15);
			boardButton[4][1].setBounds(308,310,15,15);
			boardButton[4][0].setBounds(295,340,15,15);

			boardButton[6][10].setBounds(376,30,15,15);
			boardButton[6][9].setBounds(380,70,15,15);
			boardButton[6][8].setBounds(381,100,15,15);
			boardButton[6][7].setBounds(382,130,15,15);
			boardButton[6][6].setBounds(386,160,15,15);
			boardButton[6][5].setBounds(391,190,15,15);
			boardButton[6][4].setBounds(397,220,15,15);
			boardButton[6][3].setBounds(405,250,15,15);
			boardButton[6][2].setBounds(411,280,15,15);
			boardButton[6][1].setBounds(421,310,15,15);
			boardButton[6][0].setBounds(434,340,15,15);

			boardButton[5][12].setBounds(366,30,15,15);
			boardButton[5][11].setBounds(366,65,15,15);
			boardButton[5][10].setBounds(366,90,15,15);
			boardButton[5][9].setBounds(366,115,15,15);
			boardButton[5][8].setBounds(364,140,15,15);
			boardButton[5][7].setBounds(364,165,15,15);
			boardButton[5][6].setBounds(364,190,15,15);
			boardButton[5][5].setBounds(364,215,15,15);
			boardButton[5][4].setBounds(363,240,15,15);
			boardButton[5][3].setBounds(362,265,15,15);
			boardButton[5][2].setBounds(360,290,15,15);
			boardButton[5][1].setBounds(359,315,15,15);
			boardButton[5][0].setBounds(358,340,15,15);

			for(int i=0;i<11;i++){
				for(int k=0;k<13;k++){
					boardButton[i][k].setIcon(nothing);;//0は何もない状態
				}
			}
			for(int i=0;i<6;i++){
				int k=(2*(i+1));
				boardButton[i][k].setIcon(goal);//2はゴール地点
				boardButton[10-i][k].setIcon(goal);
			}
			this.add(boardButton[0][0]);
			this.add(boardButton[0][1]);
			this.add(boardButton[0][2]);
			this.add(boardButton[10][0]);
			this.add(boardButton[10][1]);
			this.add(boardButton[10][2]);

			this.add(boardButton[1][0]);
			this.add(boardButton[1][1]);
			this.add(boardButton[1][2]);
			this.add(boardButton[1][3]);
			this.add(boardButton[1][4]);
			this.add(boardButton[9][0]);
			this.add(boardButton[9][1]);
			this.add(boardButton[9][2]);
			this.add(boardButton[9][3]);
			this.add(boardButton[9][4]);


			this.add(boardButton[2][0]);
			this.add(boardButton[2][1]);
			this.add(boardButton[2][2]);
			this.add(boardButton[2][3]);
			this.add(boardButton[2][4]);
			this.add(boardButton[2][5]);
			this.add(boardButton[2][6]);
			this.add(boardButton[8][0]);
			this.add(boardButton[8][1]);
			this.add(boardButton[8][2]);
			this.add(boardButton[8][3]);
			this.add(boardButton[8][4]);
			this.add(boardButton[8][5]);
			this.add(boardButton[8][6]);

			this.add(boardButton[3][0]);
			this.add(boardButton[3][1]);
			this.add(boardButton[3][2]);
			this.add(boardButton[3][3]);
			this.add(boardButton[3][4]);
			this.add(boardButton[3][5]);
			this.add(boardButton[3][6]);
			this.add(boardButton[3][7]);
			this.add(boardButton[3][8]);
			this.add(boardButton[7][0]);
			this.add(boardButton[7][1]);
			this.add(boardButton[7][2]);
			this.add(boardButton[7][3]);
			this.add(boardButton[7][4]);
			this.add(boardButton[7][5]);
			this.add(boardButton[7][6]);
			this.add(boardButton[7][7]);
			this.add(boardButton[7][8]);

			this.add(boardButton[4][0]);
			this.add(boardButton[4][1]);
			this.add(boardButton[4][2]);
			this.add(boardButton[4][3]);
			this.add(boardButton[4][4]);
			this.add(boardButton[4][5]);
			this.add(boardButton[4][6]);
			this.add(boardButton[4][7]);
			this.add(boardButton[4][8]);
			this.add(boardButton[4][9]);
			this.add(boardButton[4][10]);
			this.add(boardButton[6][0]);
			this.add(boardButton[6][1]);
			this.add(boardButton[6][2]);
			this.add(boardButton[6][3]);
			this.add(boardButton[6][4]);
			this.add(boardButton[6][5]);
			this.add(boardButton[6][6]);
			this.add(boardButton[6][7]);
			this.add(boardButton[6][8]);
			this.add(boardButton[6][9]);
			this.add(boardButton[6][10]);

			this.add(boardButton[5][0]);
			this.add(boardButton[5][1]);
			this.add(boardButton[5][2]);
			this.add(boardButton[5][3]);
			this.add(boardButton[5][4]);
			this.add(boardButton[5][5]);
			this.add(boardButton[5][6]);
			this.add(boardButton[5][7]);
			this.add(boardButton[5][8]);
			this.add(boardButton[5][9]);
			this.add(boardButton[5][10]);
			this.add(boardButton[5][11]);
			this.add(boardButton[5][12]);

			label6 = new JLabel("");
			label6.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label6.setBounds(580, 510, 30, 30);
			this.add(label6);
			label7 = new JLabel("");
			label7.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label7.setBounds(670, 510, 30, 30);
			this.add(label7);
			label8 = new JLabel("");
			label8.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label8.setBounds(580, 590, 30, 30);
			this.add(label8);
			label9 = new JLabel("");
			label9.setFont(new Font("Meiryo UI", Font.BOLD, 25));
			label9.setBounds(670, 590, 30, 30);
			this.add(label9);

			label10 = new JLabel("登山する道の番号を選んでください");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 20));
			label10.setBounds(30, 488, 350, 30);
			this.add(label10);



			roadSelectButton1.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roadSelectButton1.setForeground(Color.BLACK);
			roadSelectButton1.setContentAreaFilled(false);
			roadSelectButton1.setBorderPainted(false);
			roadSelectButton1.setEnabled(false);
			roadSelectButton1.setBounds(20, 520, 150, 50);
			this.add(roadSelectButton1);
			roadSelectButton2.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roadSelectButton2.setForeground(Color.BLACK);
			roadSelectButton2.setContentAreaFilled(false);
			roadSelectButton2.setBorderPainted(false);
			roadSelectButton2.setEnabled(false);
			roadSelectButton2.setBounds(20, 590, 150, 50);
			this.add(roadSelectButton2);
			roadSelectButton3.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roadSelectButton3.setForeground(Color.BLACK);
			roadSelectButton3.setContentAreaFilled(false);
			roadSelectButton3.setBorderPainted(false);
			roadSelectButton3.setEnabled(false);
			roadSelectButton3.setBounds(180, 520, 150, 50);
			this.add(roadSelectButton3);
			roadSelectButton4.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roadSelectButton4.setForeground(Color.BLACK);
			roadSelectButton4.setContentAreaFilled(false);
			roadSelectButton4.setBorderPainted(false);
			roadSelectButton4.setEnabled(false);
			roadSelectButton4.setBounds(180, 590, 150, 50);
			this.add(roadSelectButton4);
			roadSelectButton5.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roadSelectButton5.setForeground(Color.BLACK);
			roadSelectButton5.setContentAreaFilled(false);
			roadSelectButton5.setBorderPainted(false);
			roadSelectButton5.setEnabled(false);
			roadSelectButton5.setBounds(340, 520, 150, 50);
			this.add(roadSelectButton5);
			roadSelectButton6.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			roadSelectButton6.setForeground(Color.BLACK);
			roadSelectButton6.setContentAreaFilled(false);
			roadSelectButton6.setBorderPainted(false);
			roadSelectButton6.setEnabled(false);
			roadSelectButton6.setBounds(340, 590, 150, 50);
			this.add(roadSelectButton6);


			//テキストフィールド
		  	gameLog1 = new JTextField(log1,20);
		  	gameLog1.setFont(new Font("メイリオ", Font.BOLD, 15));
		  	gameLog1.setOpaque(true);
		  	gameLog1.setBounds(15, 435, 700, 30);
		  	this.add(gameLog1);

		  	label3 = new JLabel(bgText);
			label3.setBounds(720, 10, 252,270);
			this.add(label3);




			label5 = new JLabel(gameUnder);
			label5.setBounds(17, 420, 700, 300);
			this.add(label5);


			//背景の変更
			if(numOfMountain==1){
				label4 = new JLabel(gameFuji);
				label4.setBounds(-135, -140, 1000, 700);
				this.add(label4);
			}
			if(numOfMountain==2){
				label4 = new JLabel(gameEv);
				label4.setBounds(-135, -140, 1000, 700);
				this.add(label4);
			}
			if(numOfMountain==3){
				label4 = new JLabel(gameMa);
				label4.setBounds(-135, -140, 1000, 700);
				this.add(label4);
			}
			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);

			this.validate();
		}
		//ゲームオーバー画面の表示

		public void setLog(int num){
			if(num==1){
				log1="バーストしました";
			}

			if(num==2){
				log1="行動を選択してください";
			}
			if(num==3){
				log1="道を選択してください";
			}
			//イベント関係のログ
			if(num==11){
				log1="野犬に襲われました。2時間経過";
			}
			if(num==12){
				log1="道が良く見えず崖から落ちた。2時間経過";
			}
			if(num==13){
				log1="熊に襲われた。ゲームオーバー";
			}
			if(num==14){
				log1="雨が降ってきた。休憩するまで1時間余分に経過";
			}
			if(num==15){
				log1="雨が降ってきた。２時間経過";
			}
			if(num==16){
				log1="凍傷にかかった。ゲームオーバー";
			}
			if(num==17){
				log1="吹雪が起きて足止めされてしまった。3時間経過";
			}
			if(num==18){
				log1="雪崩が起きた。4時間経過";
			}
			if(num==19){
				log1="高山病にかかった。6時間経過";
			}
			gameLog1.setText(log1);

			this.validate();
		}
		//ボタンを押せなくする(木を揺らすボタンは押せるように)
		public void enabledButton(){
			treeButton.setEnabled(true);
			restButton.setEnabled(true);
			roadSelectButton1.setEnabled(false);
			roadSelectButton2.setEnabled(false);
			roadSelectButton3.setEnabled(false);
			roadSelectButton4.setEnabled(false);
			roadSelectButton5.setEnabled(false);
			roadSelectButton6.setEnabled(false);
		}

		//盤面と背景（昼夜）と時間の更新
		public void renewButton(){
			//盤面ボタンの設定
			int[][] board=new int[11][13];
			int time=mountain.getTime();
			board=mountain.getBoard();

			System.out.println("time="+time);
			for(int i=0;i<11;i++){
				for(int k=0;k<13;k++){
					//何もない時
					if(board[i][k]==0){
						boardButton[i][k].setIcon(nothing);
					}
					//登山者がいるとき
					if(board[i][k]==1){
						boardButton[i][k].setIcon(team);
					}
					//2はゴール地点
					if(board[i][k]==2){
						boardButton[i][k].setIcon(goal);
					}
					//3はゴールした道
					if(board[i][k]==3){
						boardButton[i][k].setIcon(goalLine);
					}
					//4は道すらないとこ
					if(board[i][k]==4){
					}
					//5はスタート地点(休憩時に更新される
					if(board[i][k]==5){
						boardButton[i][k].setIcon(rest);
					}
				}
			}
			//背景の更新

			if(time%24==0||time%24==1||time%24==2||time%24==3||time%24==4||
					time%24==5||time%24==6||time%24==7||time%24==8||
					time%24==9||time%24==10||time%24==11){
				if(numOfMountain==1){
					label4.setIcon(gameFuji);
				}
				if(numOfMountain==2){
					label4.setIcon(gameEv);
				}
				if(numOfMountain==3){
					label4.setIcon(gameMa);
				}
			}
			else{
				if(numOfMountain==1){
					label4.setIcon(gameNightFuji);
				}
				if(numOfMountain==2){
					label4.setIcon(gameNightEv);
				}
				if(numOfMountain==3){
					label4.setIcon(gameNightMa);
				}
			}

			//時間の更新
			day=String.valueOf(time/24);
			hour=String.valueOf(time%24);
			label12.setText(day);
			label13.setText(hour);
			//何時間経ったか


			this.validate();
		}

		public void paintDice(){
			String d1 = String.valueOf(dice[0]);
			String d2 = String.valueOf(dice[1]);
			String d3 = String.valueOf(dice[2]);
			String d4 = String.valueOf(dice[3]);

			label6.setText(d1);
			label7.setText(d2);
			label8.setText(d3);
			label9.setText(d4);

			this.validate();
		}
		public void paintSelectNum(){
			roadSelectButton1.setContentAreaFilled(false);
			roadSelectButton1.setBorderPainted(false);
			roadSelectButton1.setEnabled(false);
			roadSelectButton1.setText("");
			roadSelectButton2.setContentAreaFilled(false);
			roadSelectButton2.setBorderPainted(false);
			roadSelectButton2.setEnabled(false);
			roadSelectButton2.setText("");
			roadSelectButton3.setContentAreaFilled(false);
			roadSelectButton3.setBorderPainted(false);
			roadSelectButton3.setEnabled(false);
			roadSelectButton3.setText("");
			roadSelectButton4.setContentAreaFilled(false);
			roadSelectButton4.setBorderPainted(false);
			roadSelectButton4.setEnabled(false);
			roadSelectButton4.setText("");
			roadSelectButton5.setContentAreaFilled(false);
			roadSelectButton5.setBorderPainted(false);
			roadSelectButton5.setEnabled(false);
			roadSelectButton5.setText("");
			roadSelectButton6.setContentAreaFilled(false);
			roadSelectButton6.setBorderPainted(false);
			roadSelectButton6.setEnabled(false);
			roadSelectButton6.setText("");


			int h=1;
			String d1 ,d2;
			String d3="  ";
			for(int i=0;i<6;i++){
			System.out.println(selectNum[i][0]);
			System.out.println(selectNum[i][1]);
			}
			for(int i=0;i<6;i++){
				if(selectNum[i][0]==-1&&selectNum[i][1]==-1){
				}
				else if(selectNum[i][0]>=2&&selectNum[i][1]==-1){
					d1 = String.valueOf(selectNum[i][0]);
					if(h==1){
						roadSelectButton1.setContentAreaFilled(true);
						roadSelectButton1.setBorderPainted(true);
						roadSelectButton1.setEnabled(true);
						roadSelectButton1.setText(d1);
					}
					else if(h==2){
						roadSelectButton2.setContentAreaFilled(true);
						roadSelectButton2.setBorderPainted(true);
						roadSelectButton2.setEnabled(true);
						roadSelectButton2.setText(d1);
					}
					else if(h==3){
						roadSelectButton3.setContentAreaFilled(true);
						roadSelectButton3.setBorderPainted(true);
						roadSelectButton3.setEnabled(true);
						roadSelectButton3.setText(d1);
					}
					else if(h==4){
						roadSelectButton4.setContentAreaFilled(true);
						roadSelectButton4.setBorderPainted(true);
						roadSelectButton4.setEnabled(true);
						roadSelectButton4.setText(d1);
					}
					else if(h==5){
						roadSelectButton5.setContentAreaFilled(true);
						roadSelectButton5.setBorderPainted(true);
						roadSelectButton5.setEnabled(true);
						roadSelectButton5.setText(d1);
					}
					else if(h==6){
						roadSelectButton6.setContentAreaFilled(true);
						roadSelectButton6.setBorderPainted(true);
						roadSelectButton6.setEnabled(true);
						roadSelectButton6.setText(d1);
					}
					h++;
				}
				else if(selectNum[i][0]>=2&&selectNum[i][1]>=2&&selectNum[i][0]!=selectNum[i][1]){
					d1 = String.valueOf(selectNum[i][0]);
					d2 = String.valueOf(selectNum[i][1]);
					String g;
					g=d1+d3+d2;

					if(h==1){
						roadSelectButton1.setContentAreaFilled(true);
						roadSelectButton1.setBorderPainted(true);
						roadSelectButton1.setEnabled(true);
						roadSelectButton1.setText(g);
					}
					else if(h==2){
						roadSelectButton2.setContentAreaFilled(true);
						roadSelectButton2.setBorderPainted(true);
						roadSelectButton2.setEnabled(true);
						roadSelectButton2.setText(g);
					}
					else if(h==3){
						roadSelectButton3.setContentAreaFilled(true);
						roadSelectButton3.setBorderPainted(true);
						roadSelectButton3.setEnabled(true);
						roadSelectButton3.setText(g);
					}
					else if(h==4){
						roadSelectButton4.setContentAreaFilled(true);
						roadSelectButton4.setBorderPainted(true);
						roadSelectButton4.setEnabled(true);
						roadSelectButton4.setText(g);
					}
					else if(h==5){
						roadSelectButton5.setContentAreaFilled(true);
						roadSelectButton5.setBorderPainted(true);
						roadSelectButton5.setEnabled(true);
						roadSelectButton5.setText(g);
					}
					else if(h==6){
						roadSelectButton6.setContentAreaFilled(true);
						roadSelectButton6.setBorderPainted(true);
						roadSelectButton6.setEnabled(true);
						roadSelectButton6.setText(g);
					}
					h++;
				}
				else if(selectNum[i][0]<-1&&selectNum[i][0]<-1){
					d1 = String.valueOf(selectNum[i][0]);
					d2 = String.valueOf(selectNum[i][0]);
					if(h==1){
						roadSelectButton1.setContentAreaFilled(true);
						roadSelectButton1.setBorderPainted(true);
						roadSelectButton1.setEnabled(true);
						roadSelectButton1.setText(d1);
						roadSelectButton2.setContentAreaFilled(true);
						roadSelectButton2.setBorderPainted(true);
						roadSelectButton2.setEnabled(true);
						roadSelectButton2.setText(d2);
					}
					else if(h==2){
						roadSelectButton2.setContentAreaFilled(true);
						roadSelectButton2.setBorderPainted(true);
						roadSelectButton2.setEnabled(true);
						roadSelectButton2.setText(d1);
						roadSelectButton3.setContentAreaFilled(true);
						roadSelectButton3.setBorderPainted(true);
						roadSelectButton3.setEnabled(true);
						roadSelectButton3.setText(d2);
					}
					else if(h==3){
						roadSelectButton3.setContentAreaFilled(true);
						roadSelectButton3.setBorderPainted(true);
						roadSelectButton3.setEnabled(true);
						roadSelectButton3.setText(d1);
						roadSelectButton4.setContentAreaFilled(true);
						roadSelectButton4.setBorderPainted(true);
						roadSelectButton4.setEnabled(true);
						roadSelectButton4.setText(d2);
					}
					else if(h==4){
						roadSelectButton4.setContentAreaFilled(true);
						roadSelectButton4.setBorderPainted(true);
						roadSelectButton4.setEnabled(true);
						roadSelectButton4.setText(d1);
						roadSelectButton5.setContentAreaFilled(true);
						roadSelectButton5.setBorderPainted(true);
						roadSelectButton5.setEnabled(true);
						roadSelectButton5.setText(d2);
					}
					else if(h==5){
						roadSelectButton5.setContentAreaFilled(true);
						roadSelectButton5.setBorderPainted(true);
						roadSelectButton5.setEnabled(true);
						roadSelectButton5.setText(d1);
						roadSelectButton6.setContentAreaFilled(true);
						roadSelectButton6.setBorderPainted(true);
						roadSelectButton6.setEnabled(true);
						roadSelectButton6.setText(d2);
					}

					h=h+2;
				}
				else if(selectNum[i][0]>=2&&selectNum[i][1]>=2&&selectNum[i][0]==selectNum[i][1]){
					d1 = String.valueOf(selectNum[i][0]);
					if(h==1){
						roadSelectButton1.setContentAreaFilled(true);
						roadSelectButton1.setBorderPainted(true);
						roadSelectButton1.setEnabled(true);
						roadSelectButton1.setText(d1);
					}
					else if(h==2){
						roadSelectButton2.setContentAreaFilled(true);
						roadSelectButton2.setBorderPainted(true);
						roadSelectButton2.setEnabled(true);
						roadSelectButton2.setText(d1);
					}
					else if(h==3){
						roadSelectButton3.setContentAreaFilled(true);
						roadSelectButton3.setBorderPainted(true);
						roadSelectButton3.setEnabled(true);
						roadSelectButton3.setText(d1);
					}
					else if(h==4){
						roadSelectButton4.setContentAreaFilled(true);
						roadSelectButton4.setBorderPainted(true);
						roadSelectButton4.setEnabled(true);
						roadSelectButton4.setText(d1);
					}
					else if(h==5){
						roadSelectButton5.setContentAreaFilled(true);
						roadSelectButton5.setBorderPainted(true);
						roadSelectButton5.setEnabled(true);
						roadSelectButton5.setText(d1);
					}
					else if(h==6){
						roadSelectButton6.setContentAreaFilled(true);
						roadSelectButton6.setBorderPainted(true);
						roadSelectButton6.setEnabled(true);
						roadSelectButton6.setText(d1);
					}
					h++;
				}

			}
			this.validate();
		}
		//バーストしたかどうかの判定
		public int checkBurst(){
			if(selectNum[0][0]==-1){
				return 1;
			}
			else{
				return 0;
			}
		}
	}

	public void paintGameOverCanvas(){
		layout.show(container,"over");
	}

	public class GameOverPanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		//お金
		int money=0;
		String s="0";
		public GameOverPanel(){
			setLayout(null);

			label1 = new JLabel("獲得金額           円");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label1.setBounds(220, 420, 630, 30);
			this.add(label1);

			label3 = new JLabel(s);
			label3.setHorizontalAlignment(JLabel.RIGHT);
			label3.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label3.setBounds(250, 420, 200, 30);
			this.add(label3);


			label2 = new JLabel(overLog);
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label2.setBounds(220, 240, 630, 30);
			this.add(label2);

			okButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			okButton.setForeground(Color.BLACK);
			okButton.setBounds(680, 580, 250, 50);
			okButton.setBackground(Color.WHITE);
			this.add(okButton);
			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);

			System.out.println("overLogは"+overLog);

		}
		public void paint(){

			if(overNumber==1){
				money=600000/time;
			}
			else{
				money=0;
			}
			String s = String.valueOf(money);
			label3.setText(s);
			label2.setText(overLog);

			//プレイヤのお金を増やす
			int a=player.getMoney()+money;
			player.setMoney(a);
			this.validate();
		}

	}


	public void paintItemCanvas(){
		layout.show(container,"item");
	}

	public class ItemPanel extends JPanel {

	}

	public void paintRuleCanvas(){
		layout.show(container,"rule");
	}

	public class RulePanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;
		JLabel label9;
		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;
		JLabel label14;
		JLabel label15;
		JLabel label16;
		JLabel label17;
		JLabel label18;
		JLabel label19;
		public RulePanel(){
			setLayout(null);

			label1 = new JLabel("ルール説明 ");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label1.setBounds(20, 20, 330, 30);
			this.add(label1);
			label2 = new JLabel("1. 『Mountain GO!』について  ");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label2.setBounds(20, 40, 330, 30);
			this.add(label2);
			label3 = new JLabel("このゲームは数々の困難を運とアイテムを用いて乗り越え、"
					+ "なるべく早く頂上 にたどり着くのを目的としたゲームです。 ");
			label3.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label3.setBounds(20, 60, 700, 30);
			this.add(label3);
			label4 = new JLabel("2. 登山できる山について  ");
			label4.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label4.setBounds(20, 100, 330, 30);
			this.add(label4);
			label5 = new JLabel("登山に挑戦できる山は、『富士山』、 『エベレスト』、 "
					+ "『ヴィンソン・マシフ』の３種類あり、それぞれ特徴を持っています。");
			label5.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label5.setBounds(20, 120, 700, 30);
			this.add(label5);
			label6 = new JLabel("3. ゲームの流れ ");
			label6.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label6.setBounds(20, 140, 330, 30);
			this.add(label6);
			label7= new JLabel("3.1. 手番になると、まず『木を揺らす』ボタンを押すことで、1～6の 任意の数字が書かれた落ち葉が4枚落ちてきます。 ");
			label7.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label7.setBounds(20, 180, 900, 30);
			this.add(label7);
			label8 = new JLabel("3.2. これらの数字を2つずつ、2組に分けます。それぞれの組の数字の合計に対応した番号の道を、登山チームが１マス分登ります。 ");
			label8.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label8.setBounds(20, 220, 900, 30);
			this.add(label8);
			label9 = new JLabel("3.3. 両方の組の合計が同じだった場合、その合計に対応した道を２マス分登ることになります。");
			label9.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label9.setBounds(20, 260, 1000, 30);
			this.add(label9);
			label15 = new JLabel("その手番中に進める登山チームは3組までです。もしすでに3つの登山チームを置いているならば、登山チームのいる道の番号に対応した組のみ有効になります。");
			label15.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label15.setBounds(20, 280, 1000, 30);
			this.add(label15);
			label10 = new JLabel("（例：すでに登山者が３・６・７番の道にいるという状況で「３・３」「４・６」という２組にサイコロを分けたなら、６番の登山者だけを１マス登らせること");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label10.setBounds(20, 300, 1000, 30);
			this.add(label10);
			label16 = new JLabel("になります）");
			label16.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label16.setBounds(20, 320, 1000, 30);
			this.add(label16);
			label11 = new JLabel("3.4. しかし両方の組の合計に対応した番号の道がどちらも「４人目の登山者を置かざるをえない」道であったなら、登山チームは全員転落します。今回 ");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label11.setBounds(20, 360, 1000, 30);
			this.add(label11);
			label12 = new JLabel("の手番で進めたぶんは（たとえ頂上まで登っていた登山チームがいても） すべて無効になります。");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label12.setBounds(20, 380, 900, 30);
			this.add(label12);
			label13 = new JLabel("3.5. ３つのチームのうち、２つのチームがゴールできるまでにかかった時間が スコアとなります。 ");
			label13.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label13.setBounds(20, 420, 900, 30);
			this.add(label13);
			label14 = new JLabel("3.6. ゴールするとスコアに応じたお金を取得できます。 そのお金を用いて、プレイヤーは登山を有利に進めたり、登山を楽しんだりするアイテムを");
			label14.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label14.setBounds(20, 460, 900, 30);
			this.add(label14);
			label17 = new JLabel("コストが超過しない限りいくつでも持つことができます。");
			label17.setFont(new Font("Meiryo UI", Font.BOLD, 15));
			label17.setBounds(20, 480, 900, 30);
			this.add(label17);
			backToButton.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			backToButton.setForeground(Color.BLACK);
			backToButton.setBounds(680, 580, 250, 50);
			backToButton.setBackground(Color.WHITE);
			this.add(backToButton);


			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);

		}
	}

	public void paintScoreCanvas(){
		layout.show(container,"score");
	}

	public class ScorePanel extends JPanel {
		JLabel label0;
		JLabel label1;
		JLabel label2;
		JLabel label3;
		JLabel label4;
		JLabel label5;
		JLabel label6;
		JLabel label7;
		JLabel label8;
		JLabel label9;
		JLabel label10;
		JLabel label11;
		JLabel label12;
		JLabel label13;

		JLabel label20;
		JLabel label21;
		JLabel label22;
		JLabel label23;
		JLabel label24;
		JLabel label25;
		JLabel label26;
		JLabel label27;
		JLabel label28;
		JLabel label29;

		JLabel label30;
		JLabel label31;
		JLabel label32;
		JLabel label33;
		JLabel label34;
		JLabel label35;
		JLabel label36;
		JLabel label37;
		JLabel label38;
		JLabel label39;

		JLabel label40;
		JLabel label41;
		JLabel label42;
		JLabel label43;
		JLabel label44;
		JLabel label45;
		JLabel label46;
		JLabel label47;
		JLabel label48;
		JLabel label49;

		JLabel label51;
		JLabel label52;
		//お金
		int rankNum=0;
		int time=0;
		String day="0",hour="0";
		String day0="0",hour0="0";
		String day1="0",hour1="0";
		String day2="0",hour2="0";
		String day3="0",hour3="0";
		String day4="0",hour4="0";
		String day5="0",hour5="0";
		String day6="0",hour6="0";
		String day7="0",hour7="0";
		String day8="0",hour8="0";
		String day9="0",hour9="0";
		String name0="";
		String name1="";
		String name2="";
		String name3="";
		String name4="";
		String name5="";
		String name6="";
		String name7="";
		String name8="";
		String name9="";

		public ScorePanel(){
			setLayout(null);

			label1 = new JLabel("あなたのスコア");
			label1.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label1.setBounds(720, 100, 630, 30);
			this.add(label1);

			label2 = new JLabel("     日      時間");
			label2.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label2.setBounds(700, 200, 630, 30);
			this.add(label2);

			//何日立ったか
			label51 = new JLabel(day);
			label51.setHorizontalAlignment(JLabel.RIGHT);
			label51.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label51.setOpaque(false);
			label51.setBounds(645, 195, 100, 40);
			this.add(label51);
			//何時間経ったか
			label52 = new JLabel(hour);
			label52.setHorizontalAlignment(JLabel.RIGHT);
			label52.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label52.setOpaque(false);
			label52.setBounds(735, 195, 100, 40);
			this.add(label52);

			label20 = new JLabel(day0);
			label20.setHorizontalAlignment(JLabel.RIGHT);
			label20.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label20.setOpaque(false);
			label20.setBounds(385, 45, 100, 40);
			this.add(label20);
			label30 = new JLabel(hour0);
			label30.setHorizontalAlignment(JLabel.RIGHT);
			label30.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label30.setOpaque(false);
			label30.setBounds(470, 45, 100, 40);
			this.add(label30);
			label21 = new JLabel(day1);
			label21.setHorizontalAlignment(JLabel.RIGHT);
			label21.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label21.setOpaque(false);
			label21.setBounds(385, 95, 100, 40);
			this.add(label21);
			label31 = new JLabel(hour1);
			label31.setHorizontalAlignment(JLabel.RIGHT);
			label31.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label31.setOpaque(false);
			label31.setBounds(470, 95, 100, 40);
			this.add(label31);
			label22 = new JLabel(day2);
			label22.setHorizontalAlignment(JLabel.RIGHT);
			label22.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label22.setOpaque(false);
			label22.setBounds(385, 145, 100, 40);
			this.add(label22);
			label32 = new JLabel(hour2);
			label32.setHorizontalAlignment(JLabel.RIGHT);
			label32.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label32.setOpaque(false);
			label32.setBounds(470, 145, 100, 40);
			this.add(label32);
			label23 = new JLabel(day3);
			label23.setHorizontalAlignment(JLabel.RIGHT);
			label23.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label23.setOpaque(false);
			label23.setBounds(385, 195, 100, 40);
			this.add(label23);
			label33 = new JLabel(hour3);
			label33.setHorizontalAlignment(JLabel.RIGHT);
			label33.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label33.setOpaque(false);
			label33.setBounds(470, 195, 100, 40);
			this.add(label33);
			label24 = new JLabel(day4);
			label24.setHorizontalAlignment(JLabel.RIGHT);
			label24.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label24.setOpaque(false);
			label24.setBounds(385, 245, 100, 40);
			this.add(label24);
			label34 = new JLabel(hour4);
			label34.setHorizontalAlignment(JLabel.RIGHT);
			label34.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label34.setOpaque(false);
			label34.setBounds(470, 245, 100, 40);
			this.add(label34);
			label25 = new JLabel(day5);
			label25.setHorizontalAlignment(JLabel.RIGHT);
			label25.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label25.setOpaque(false);
			label25.setBounds(385, 295, 100, 40);
			this.add(label25);
			label35= new JLabel(hour5);
			label35.setHorizontalAlignment(JLabel.RIGHT);
			label35.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label35.setOpaque(false);
			label35.setBounds(470, 295, 100, 40);
			this.add(label35);
			label26 = new JLabel(day6);
			label26.setHorizontalAlignment(JLabel.RIGHT);
			label26.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label26.setOpaque(false);
			label26.setBounds(385, 345, 100, 40);
			this.add(label26);
			label36 = new JLabel(hour6);
			label36.setHorizontalAlignment(JLabel.RIGHT);
			label36.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label36.setOpaque(false);
			label36.setBounds(470, 345, 100, 40);
			this.add(label36);
			label27 = new JLabel(day7);
			label27.setHorizontalAlignment(JLabel.RIGHT);
			label27.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label27.setOpaque(false);
			label27.setBounds(385,395, 100, 40);
			this.add(label27);
			label37 = new JLabel(hour7);
			label37.setHorizontalAlignment(JLabel.RIGHT);
			label37.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label37.setOpaque(false);
			label37.setBounds(470, 395, 100, 40);
			this.add(label37);
			label28 = new JLabel(day8);
			label28.setHorizontalAlignment(JLabel.RIGHT);
			label28.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label28.setOpaque(false);
			label28.setBounds(385, 445, 100, 40);
			this.add(label28);
			label38 = new JLabel(hour8);
			label38.setHorizontalAlignment(JLabel.RIGHT);
			label38.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label38.setOpaque(false);
			label38.setBounds(470, 445, 100, 40);
			this.add(label38);
			label29 = new JLabel(day9);
			label29.setHorizontalAlignment(JLabel.RIGHT);
			label29.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label29.setOpaque(false);
			label29.setBounds(385, 495, 100, 40);
			this.add(label29);
			label39 = new JLabel(hour9);
			label39.setHorizontalAlignment(JLabel.RIGHT);
			label39.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label39.setOpaque(false);
			label39.setBounds(470, 495, 100, 40);
			this.add(label39);

			label40 = new JLabel(name0);
			label40.setHorizontalAlignment(JLabel.LEFT);
			label40.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label40.setOpaque(false);
			label40.setBounds(220, 45, 500, 40);
			this.add(label40);
			label41 = new JLabel(name1);
			label41.setHorizontalAlignment(JLabel.LEFT);
			label41.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label41.setOpaque(false);
			label41.setBounds(220, 95, 500, 40);
			this.add(label41);
			label42 = new JLabel(name2);
			label42.setHorizontalAlignment(JLabel.LEFT);
			label42.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label42.setOpaque(false);
			label42.setBounds(220, 145, 500, 40);
			this.add(label42);
			label43 = new JLabel(name3);
			label43.setHorizontalAlignment(JLabel.LEFT);
			label43.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label43.setOpaque(false);
			label43.setBounds(220, 195, 500, 40);
			this.add(label43);
			label44 = new JLabel(name4);
			label44.setHorizontalAlignment(JLabel.LEFT);
			label44.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label44.setOpaque(false);
			label44.setBounds(220, 245, 500, 40);
			this.add(label44);
			label45 = new JLabel(name5);
			label45.setHorizontalAlignment(JLabel.LEFT);
			label45.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label45.setOpaque(false);
			label45.setBounds(220, 295, 500, 40);
			this.add(label45);
			label46 = new JLabel(name6);
			label46.setHorizontalAlignment(JLabel.LEFT);
			label46.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label46.setOpaque(false);
			label46.setBounds(220, 345, 500, 40);
			this.add(label46);
			label47 = new JLabel(name7);
			label47.setHorizontalAlignment(JLabel.LEFT);
			label47.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label47.setOpaque(false);
			label47.setBounds(220, 395, 500, 40);
			this.add(label47);
			label48 = new JLabel(name8);
			label48.setHorizontalAlignment(JLabel.LEFT);
			label48.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label48.setOpaque(false);
			label48.setBounds(220, 445, 500, 40);
			this.add(label48);
			label49 = new JLabel(name9);
			label49.setHorizontalAlignment(JLabel.LEFT);
			label49.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label49.setOpaque(false);
			label49.setBounds(220, 495, 500, 40);
			this.add(label49);


			label3 = new JLabel("1位:                                 日     時間  ");
			label3.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label3.setBounds(100, 50, 630, 30);
			this.add(label3);
			label4 = new JLabel("2位:                                 日     時間  ");
			label4.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label4.setBounds(100,100, 630, 30);
			this.add(label4);
			label5 = new JLabel("3位:                                 日     時間  ");
			label5.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label5.setBounds(100, 150, 630, 30);
			this.add(label5);
			label6 = new JLabel("4位:                                 日     時間  ");
			label6.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label6.setBounds(100, 200, 630, 30);
			this.add(label6);
			label7 = new JLabel("5位:                                 日     時間  ");
			label7.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label7.setBounds(100, 250, 630, 30);
			this.add(label7);
			label8 = new JLabel("6位:                                 日     時間  ");
			label8.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label8.setBounds(100, 300, 630, 30);
			this.add(label8);
			label9 = new JLabel("7位:                                 日     時間  ");
			label9.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label9.setBounds(100, 350, 630, 30);
			this.add(label9);
			label10 = new JLabel("8位:                                 日     時間  ");
			label10.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label10.setBounds(100, 400, 630, 30);
			this.add(label10);
			label11 = new JLabel("9位:                                 日     時間  ");
			label11.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label11.setBounds(100, 450, 630, 30);
			this.add(label11);
			label12 = new JLabel("10位:                                 日     時間  ");
			label12.setFont(new Font("Meiryo UI", Font.BOLD, 30));
			label12.setBounds(80, 500, 630, 30);
			this.add(label12);

			endButton1.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			endButton1.setForeground(Color.BLACK);
			endButton1.setBounds(680, 580, 250, 50);
			endButton1.setBackground(Color.WHITE);
			this.add(endButton1);


			menuButton5.setFont(new Font("MYSTICAL", Font.BOLD, 20));
			menuButton5.setForeground(Color.BLACK);
			menuButton5.setBounds(680, 510, 250, 50);
			menuButton5.setBackground(Color.WHITE);
			this.add(menuButton5);


			label0 = new JLabel(bgA);
			label0.setBounds(0, 0, 1000, 700);
			this.add(label0);



		}
		public void score(int a){
			time=a;
			 day=String.valueOf(a/24);
			 hour=String.valueOf(a%24);
			 label51.setText(day);
			 label52.setText(hour);
			this.validate();
		}
		public void paintRank(String name,int score){
			int day=score/24;
			int hour=score%24;

			if(rankNum==0){
				label20.setText(String.valueOf(day));
				label30.setText(String.valueOf(hour));
				label40.setText(name);
			}
			if(rankNum==1){
				label21.setText(String.valueOf(day));
				label31.setText(String.valueOf(hour));
				label41.setText(name);
			}
			if(rankNum==2){
				label22.setText(String.valueOf(day));
				label32.setText(String.valueOf(hour));
				label42.setText(name);
			}
			if(rankNum==3){
				label23.setText(String.valueOf(day));
				label33.setText(String.valueOf(hour));
				label43.setText(name);
			}
			if(rankNum==4){
				label24.setText(String.valueOf(day));
				label34.setText(String.valueOf(hour));
				label44.setText(name);
			}
			if(rankNum==5){
				label25.setText(String.valueOf(day));
				label35.setText(String.valueOf(hour));
				label45.setText(name);
			}
			if(rankNum==6){
				label26.setText(String.valueOf(day));
				label36.setText(String.valueOf(hour));
				label46.setText(name);
			}
			if(rankNum==7){
				label27.setText(String.valueOf(day));
				label37.setText(String.valueOf(hour));
				label47.setText(name);
			}
			if(rankNum==8){
				label28.setText(String.valueOf(day));
				label38.setText(String.valueOf(hour));
				label48.setText(name);
			}
			if(rankNum==9){
				label29.setText(String.valueOf(day));
				label39.setText(String.valueOf(hour));
				label49.setText(name);
			}
			this.validate();
			rankNum++;
		}
	}

	public class Item{
		String name;
		int cost;
		int ITEM_NUM;
		int value;
		Item(String name,int ITEM_NUM,int cost,int value){
			this.name=name;
			this.cost=cost;
			this.ITEM_NUM=ITEM_NUM;
			this.value=value;
		}
		public int getValue(){
			return this.value;
		}

	}
}
