// ICarVolumeCallback.aidl
package wayos.car.media;

// Declare any non-default types here with import statements
/**
* Binder interface to callback the volume key events.
*/
interface ICarAudioEffectCallback {
    /**
     * This is called whenever audio Balace is changed.
     */
    void onBalaceChange(int flags);

    /**
     * This is called whenever the Equalizer state is changed.
     */
    void onEqualizerChange(int type,int flags);
}
