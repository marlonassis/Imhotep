package br.com.imhotep.testes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;



public class Teste {
	public static void main(String[] args) {
//		int v = 8 + r.nextInt(17); 
		int max = 0;
		for(int i = 0; i<1000;i++){
			int a = new Random().nextInt(59);
			if(a > max)
				max = a;
			System.out.println(a);
		}
		System.out.println(max);
	}

}
