/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.janelas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.jogo.Jogo;
import java.util.ArrayList;

/**
 *
 * @author fabio
 */
public class JanelaConfiguracoes extends Janela{
    public JanelaConfiguracoes(){
        super();
        
         botoes = new ArrayList<Botao>();
        //Botão para iniciar acao partida.
        botoes.add(new Botao("audio.png", 100, 120,new BASom(BAMudarTela.MENU)));
        botoes.add(new Botao("audioMudo.png", 200, 120,new BASom(BAMudarTela.MENU)));
        botoes.add(new Botao("iniciar.png", 650, 20,new BAMudarTela(BAMudarTela.MENU)));
        botoes.add(new Botao("facil.png", 100, 220,new BAMudarTela(BAMudarTela.MENU)));
        botoes.add(new Botao("medio.png", 200, 220,new BAMudarTela(BAMudarTela.MENU)));
        botoes.add(new Botao("dificil.png", 300, 220,new BAMudarTela(BAMudarTela.MENU)));
        fundo = new Image(new Texture("opcoes.png"));
        fundo.addAction(Actions.sequence(Actions.fadeOut(0),Actions.delay(1),Actions.fadeIn(1)));
        estagio.addActor(fundo);
        for (Botao botao : botoes) {
            botao.imagem.addAction(Actions.sequence(Actions.fadeOut(0),Actions.delay(2),Actions.fadeIn(1)));
            estagio.addActor(botao.imagem);
        }
    }
    @Override
    public void show() {
    }


    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

    @Override
    void processa() {
        
    }
    
}
