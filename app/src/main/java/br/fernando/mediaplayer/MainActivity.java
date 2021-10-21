package br.fernando.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar volume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * cria o media player
         * */

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        inicializarSeekBar();

    }

    private void inicializarSeekBar() {

        volume = findViewById(R.id.seekVolume);

        /*configurar o audio manager
         * para descobiri o volume maximo*/
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        /*configurar a seekbar para mostrar o volume maximo*/
        volume.setMax(volumeMaximo);

        /*configurar a seekbar para mostrar o progresso atual*/
        volume.setProgress(volumeAtual);

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                /*flags fazem configuracoes adicionais*/
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void executarSom(View view) {
        if (mediaPlayer != null) {
            /*executa o som*/
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view) {
        if (mediaPlayer.isPlaying()) {
            /*pausa a musica*/
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer.isPlaying()) {
            /*pausa a musica*/
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view) {
        if (mediaPlayer.isPlaying()) {
            /*para o som*/
            mediaPlayer.stop();
            /*configura o media player para ficar pronto para executar novamente a musica*/
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bach);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            /*liberar recursos de memoria para o dispositivo*/
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}