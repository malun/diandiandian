package org.crazyit.link;
//Download by http://www.codefans.net
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.link.board.AbstractBoard;
import org.crazyit.link.board.GameService;
import org.crazyit.link.board.impl.GameServiceImpl;
import org.crazyit.link.object.GameConf;
import org.crazyit.link.object.LinkInfo;
import org.crazyit.link.view.GameView;
import org.crazyit.link.view.Piece;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */

public class Link extends Activity
{
	
	private GameConf config;
	  
	private GameService gameService;
	
	private GameView gameView;
	
	private Button startButton;
	
	private Button pauseButton;
	
	private Button refreshButton;
	
	private TextView timeTextView;

	private AlertDialog.Builder lostDialog;
	
	private AlertDialog.Builder successDialog;
	
	private AlertDialog.Builder pauseDialog;

	private Timer timer = new Timer();

	private int gameTime;

	private boolean isPlaying;

	private Vibrator vibrator;
	
	private Piece selected = null;
	
	private MediaPlayer player = null;

	private SoundPool sound = null;
	private HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer ,Integer>();
	
	
	
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
				case 0x123:
					timeTextView.setText("剩余时间： " + gameTime);
					gameTime--;
			
					if (gameTime < 0)
					{
						stopTimer();
					
						isPlaying = false;
						sound.play(soundPoolMap.get(3), 1, 1, 1, 0, 1);
						lostDialog.show();
						return;
					}
					break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		init();
	}

	
	private void init()
	{
		config = new GameConf(8, 9, 2, 10 , 100000, this);
		
		gameView = (GameView) findViewById(R.id.gameView);
	
		timeTextView = (TextView) findViewById(R.id.timeText);
	
		startButton = (Button) this.findViewById(R.id.startButton);

		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		gameService = new GameServiceImpl(this.config);
		gameView.setGameService(gameService);
		pauseButton = (Button)findViewById(R.id.pauseButton);
	
		refreshButton = (Button)findViewById(R.id.refreshButton);

		player = MediaPlayer.create(this, R.raw.bg);
		player.setLooping(true);
		player.start();

		sound = new SoundPool(10,AudioManager.STREAM_MUSIC,10);
		soundPoolMap.put(1, sound.load(this, R.raw.ready, 1));
		soundPoolMap.put(2, sound.load(this, R.raw.win, 1));
		soundPoolMap.put(3, sound.load(this, R.raw.lose, 1));
		sound.play(soundPoolMap.get(1), 1, 1, 1, 0, 1);
		startGame(GameConf.DEFAULT_TIME);
		

		startButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View source)
			{
				startGame(GameConf.DEFAULT_TIME);
				sound.play(soundPoolMap.get(1), 1, 1, 1, 0, 1);
				
				
			}
		});
		
		pauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pauseGame();
				gameView.setVisibility(View.INVISIBLE);
				player.pause();
				pauseDialog.show();
				
			}

			
		});
		
		refreshButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refreshPices();
				
			}

			private void refreshPices() {
				// TODO Auto-generated method stub
				gameView.refresh();
				gameView.postInvalidate();
			}

		
			
		});

		this.gameView.setOnTouchListener(new View.OnTouchListener()
		{
			public boolean onTouch(View view, MotionEvent e)
			{
				if (e.getAction() == MotionEvent.ACTION_DOWN)
				{
					gameViewTouchDown(e);
				}
				if (e.getAction() == MotionEvent.ACTION_UP)
				{
					gameViewTouchUp(e);
				}
				return true;
			}
		});
	
		lostDialog = createDialog("Lost", "游戏失败！ 重新开始", R.drawable.lost)
			.setPositiveButton("确定", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					startGame(GameConf.DEFAULT_TIME);
				}
			});
	
		successDialog = createDialog("Success", "游戏胜利！ 重新开始",
			R.drawable.success).setPositiveButton("确定",
			new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					startGame(GameConf.DEFAULT_TIME);
				}
			});
	
		pauseDialog = createDialog("pause", "游戏暂停！", R.drawable.p23)
			.setPositiveButton("返回", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					gameView.setVisibility(View.VISIBLE);
					startGame(gameTime);
					player.start();
				}
			});
		
	}
	@Override
	protected void onPause()
	{

		stopTimer();
		if (player.isPlaying())
		{
			player.pause();
		}
		
		super.onPause();
	}
	@Override
	protected void onResume()
	{
		

		if (isPlaying)
		{
			
	
			startGame(gameTime);
			player.start();
		}
		super.onResume();
	}

	private void pauseGame() {
		// TODO Auto-generated method stub
		stopTimer();
	}

	private void gameViewTouchDown(MotionEvent event)
	{
	
		Piece[][] pieces = gameService.getPieces();
	
		float touchX = event.getX();

		float touchY = event.getY();
	
		Piece currentPiece = gameService.findPiece(touchX, touchY);

		if (currentPiece == null)
			return;
	
		this.gameView.setSelectedPiece(currentPiece);

		if (this.selected == null)
		{
	
			this.selected = currentPiece;
			this.gameView.postInvalidate();
			return;
		}

		if (this.selected != null)
		{

			LinkInfo linkInfo = this.gameService.link(this.selected,
				currentPiece);
	
			if (linkInfo == null)
			{
	
				this.selected = currentPiece;
				this.gameView.postInvalidate();
			}
			else
			{

				handleSuccessLink(linkInfo, this.selected
					, currentPiece, pieces);
			}
		}
	}
	
	private void gameViewTouchUp(MotionEvent e)
	{
		this.gameView.postInvalidate();
	}
	

	private void startGame(int gameTime)

	{
	
		if (this.timer != null)
		{
			stopTimer();
		}
	
		this.gameTime = gameTime;		
	
		if(gameTime == GameConf.DEFAULT_TIME)
		{
			
			gameView.startGame();
		}
		isPlaying = true;	
		this.timer = new Timer();
	
		this.timer.schedule(new TimerTask()
		{
			public void run()
			{
				handler.sendEmptyMessage(0x123);
			}
		}, 0, 1000);
	
		this.selected = null;
	}	

	
	/**
	 * 成功连接后处理
	 * 
	 * @param linkInfo 连接信息
	 * @param prePiece 前一个选中方块
	 * @param currentPiece 当前选择方块
	 * @param pieces 系统中还剩的全部方块
	 */
	private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
		Piece currentPiece, Piece[][] pieces)
	{
	
		this.gameView.setLinkInfo(linkInfo);
		
		this.gameView.setSelectedPiece(null);
		this.gameView.postInvalidate();
		
		pieces[prePiece.getIndexX()][prePiece.getIndexY()] = null;
		pieces[currentPiece.getIndexX()][currentPiece.getIndexY()] = null;
		
		this.selected = null;
	
		this.vibrator.vibrate(100);
	
		if (!this.gameService.hasPieces())
		{
		
			sound.play(soundPoolMap.get(2), 1, 1, 1, 0, 1);
			this.successDialog.show();
	
			stopTimer();

			isPlaying = false;
		}
	}


	private AlertDialog.Builder createDialog(String title, String message,
		int imageResource)
	{
		return new AlertDialog.Builder(this).setTitle(title)
			.setMessage(message).setIcon(imageResource);
	}
	private void stopTimer()
	{

		this.timer.cancel();
		this.timer = null;
	}
}