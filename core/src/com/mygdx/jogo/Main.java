package com.mygdx.jogo;

import Exemplos.JanelaExemplo;
import com.badlogic.gdx.Game;
import com.mygdx.janelas.JanelaMenu;

public class Main extends Game  {
    
        
	
	@Override
	public void create () {
		setScreen(new JanelaMenu());
	}

	@Override
	public void render () {
		super.render();
	}
}
