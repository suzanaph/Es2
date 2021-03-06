/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.janelas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 *
 * @author fabio
 */
public class Botao {
    Actor imagem;
    BotaoAcao acao;
    private boolean clicado;
    private Color original;
    public Botao(String url,int x ,int y,BotaoAcao a){
        this.imagem =  new Image(new Texture(url));
        this.imagem.setPosition(x, y);
        this.acao = a;
        this.original = imagem.getColor();
    };
    public void foiClicado(Actor entrada){
       if(!clicado){
            if(entrada != null && entrada.equals(imagem)){
                clicado = true;// marca que o botão foi clicado
                Gdx.audio.newSound(Gdx.files.internal("audios/click.wav")).play();
                imagem.addAction(Actions.sequence(
                        Actions.color(Color.GREEN),
                        Actions.delay(0.2f),
                        Actions.color(original),
                        Actions.delay(0.2f)
                ));
            }
        } else if(imagem.getActions().size==0){
                acao.realizar();
                clicado = false;
                entrada = null;
        }
                
    }
   

    public Actor getImagem() {
        return imagem;
    }

    public void setImagem(Actor imagem) {
        this.imagem = imagem;
    }

    public BotaoAcao getAcao() {
        return acao;
    }

    public void setAcao(BotaoAcao acao) {
        this.acao = acao;
    }

    public boolean isClicado() {
        return clicado;
    }

    public void setClicado(boolean clicado) {
        this.clicado = clicado;
    }
    
}
