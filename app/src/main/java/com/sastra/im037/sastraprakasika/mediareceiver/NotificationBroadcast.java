package com.sastra.im037.sastraprakasika.mediareceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import com.sastra.im037.sastraprakasika.mediacontrols.Controls;
import com.sastra.im037.sastraprakasika.mediaservice.SongService;
import com.sastra.im037.sastraprakasika.mediautil.PlayerConstants;

//import com.example.im037.sastraprakasika.Mediaactivity.Mediaplay_MainActivity;


public class NotificationBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                	if(!PlayerConstants.SONG_PAUSED){
    					Controls.pauseControl(context);
                	}else{
    					Controls.playControl(context);
                	}
                	break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                	break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                	break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                	break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                	Log.d("TAG", "TAG: KEYCODE_MEDIA_NEXT");
                	Controls.nextControl(context);
                	break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                	Log.d("TAG", "TAG: KEYCODE_MEDIA_PREVIOUS");
                	Controls.previousControl(context);
                	break;
            }
		}  else{
            	if (intent.getAction().equals(SongService.NOTIFY_PLAY)) {
    				Controls.playControl(context);
        		} else if (intent.getAction().equals(SongService.NOTIFY_PAUSE)) {
    				Controls.pauseControl(context);
        		} else if (intent.getAction().equals(SongService.NOTIFY_NEXT)) {
        			Controls.nextControl(context);
        		} else if (intent.getAction().equals(SongService.NOTIFY_DELETE)) {
					Intent i = new Intent(context, SongService.class);
					context.stopService(i);
					//hide me
//					Intent intent1 = new Intent(context, LecturesFragment_Audioplay.class);
//					intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					context.startActivity(intent1);
//					Intent in = new Intent(context, Mediaplay_MainActivity.class);
//			        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			        context.startActivity(in);
        		}else if (intent.getAction().equals(SongService.NOTIFY_PREVIOUS)) {
    				Controls.previousControl(context);
        		}
		}
	}
	
	public String ComponentName() {
		return this.getClass().getName(); 
	}
}
