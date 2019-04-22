package com.group.game.Sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.IntMap;

public class soundLink {
    private static IntMap<Sound> soundKeys;

    public soundLink() {
        soundKeys = new IntMap<Sound>();

        //Create new sounds variables
        Sound badgePickUp = Gdx.audio.newSound(Gdx.files.internal("sfx/badgePickUp.mp3"));


        //Assigning sounds to a specific key
        soundKeys.put(1, badgePickUp);

    }

    public static boolean play(int keycode) {
        Sound sound = soundKeys.get(keycode);
        if (sound != null) {
            sound.play();
        }
        return false;
    }

    public static boolean isLooping(int keycode, boolean looping) {
        Sound sound = soundKeys.get(keycode);
        if (sound != null && looping == true) {
            sound.setLooping(keycode, looping);
            sound.loop();
        }
        return false;
    }

    public void dispose() {
        for (Sound sound : soundKeys.values()) {
            sound.dispose();
        }
    }
}
