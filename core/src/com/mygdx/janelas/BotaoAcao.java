/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.janelas;

/**
 *
 * @author fabio
 */
public abstract class BotaoAcao {
    String arg;
    public BotaoAcao(String arg){
        this.arg=arg;
    };
    public abstract void ativar();
}
