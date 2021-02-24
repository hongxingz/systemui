package wayos.car.media;

import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Bundle;
import android.annotation.IntDef;
import android.car.media.CarAudioManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CarAudioRequestFactory {

    public static final int CAR_AUDIO_STREAM_TYPE_E_CALL = 1;
    public static final int CAR_AUDIO_STREAM_TYPE_B_CALL = 2;
    public static final int CAR_AUDIO_STREAM_TYPE_BT_CALL = 3;
    public static final int CAR_AUDIO_STREAM_TYPE_APA = 4;
    public static final int CAR_AUDIO_STREAM_TYPE_SPEECH = 5;
    public static final int CAR_AUDIO_STREAM_TYPE_NAVIGATION = 6;
    public static final int CAR_AUDIO_STREAM_TYPE_MEDIA = 7;
    public static final int CAR_AUDIO_STREAM_TYPE_NOTIFICATION = 8;


    @IntDef({
            CAR_AUDIO_STREAM_TYPE_E_CALL,
            CAR_AUDIO_STREAM_TYPE_B_CALL,
            CAR_AUDIO_STREAM_TYPE_BT_CALL,
            CAR_AUDIO_STREAM_TYPE_APA,
            CAR_AUDIO_STREAM_TYPE_SPEECH,
            CAR_AUDIO_STREAM_TYPE_NAVIGATION,
            CAR_AUDIO_STREAM_TYPE_MEDIA,
    })
    @Retention(RetentionPolicy.SOURCE)

    public @interface AudioStreamType {}

    //  public int requestAudioFocus(@NonNull AudioFocusRequest focusRequest) 
    public static AudioFocusRequest createAudioRequest(@AudioStreamType int streamType, AudioManager.OnAudioFocusChangeListener l){
        AudioFocusRequest request = null;
        switch (streamType){
            case CAR_AUDIO_STREAM_TYPE_E_CALL:{
                Bundle bundle = new Bundle();
                bundle.putInt(CarAudioManager.AUDIOFOCUS_EXTRA_REQUEST_CALL_TYPE,CarAudioManager.CALL_TYPE_EMERGENCY);
                request = new AudioFocusRequest
                        .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                                .addBundle(bundle)
                                 .build())
                        .setForceDucking(false)
                        .setOnAudioFocusChangeListener(l)
                        .build();
                }
                break;
            case CAR_AUDIO_STREAM_TYPE_B_CALL:{
                Bundle bundle = new Bundle();
                bundle.putInt(CarAudioManager.AUDIOFOCUS_EXTRA_REQUEST_CALL_TYPE,CarAudioManager.CALL_TYPE_BREAKDOWN);
                request = new AudioFocusRequest
                        .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                                .addBundle(bundle)
                                .build())
                        .setForceDucking(false)
                        .setOnAudioFocusChangeListener(l)
                        .build();
                }
                    break;
            case CAR_AUDIO_STREAM_TYPE_APA:{
                request = new AudioFocusRequest
                        .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                        .setAudioAttributes(new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM) .build())
                        .setForceDucking(false)
                        .setOnAudioFocusChangeListener(l)
                        .build();
                 }
                break;
          
            case CAR_AUDIO_STREAM_TYPE_BT_CALL:{
                Bundle bundle = new Bundle();
                bundle.putInt(CarAudioManager.AUDIOFOCUS_EXTRA_REQUEST_CALL_TYPE,CarAudioManager.CALL_TYPE_BT);
                request = new AudioFocusRequest
                    .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                            .addBundle(bundle)
                            .build())
                    .setForceDucking(false)
                    .setOnAudioFocusChangeListener(l)
                    .build();
                }
                break;
           
            case CAR_AUDIO_STREAM_TYPE_SPEECH:
                request = new AudioFocusRequest
                    .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANT) .build())
                    .setForceDucking(false)
                    .setOnAudioFocusChangeListener(l)
                    .build();
                break;
         
            case CAR_AUDIO_STREAM_TYPE_NAVIGATION:
                request = new AudioFocusRequest
                    .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                    .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_NAVIGATION_GUIDANCE)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build())
                    .setForceDucking(false)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(l)
                    .build();
                break;
            case CAR_AUDIO_STREAM_TYPE_MEDIA:
                request = new AudioFocusRequest
                    .Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA) .build())
                    .setForceDucking(false)
                    .setOnAudioFocusChangeListener(l)
                    .build();
                break;
            case CAR_AUDIO_STREAM_TYPE_NOTIFICATION:
                request = new AudioFocusRequest
                    .Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK)
                    .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build())
                    .setForceDucking(false)
                    .setAcceptsDelayedFocusGain(true)
                    .setOnAudioFocusChangeListener(l)
                    .build();  
            break;
        }
        return request;
    }

    public static AudioAttributes CreateAudioAttributes(@AudioStreamType int streamType) {
        return createAudioRequest(streamType,null).getAudioAttributes();
    }

}
