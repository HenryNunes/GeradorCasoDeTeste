package com.gct.utilities;

public class Tupla<M, N> {
	M primeiro;
	N segundo;
	
	public Tupla(M primeiro, N segundo)
	{
		this.primeiro = primeiro;
		this.segundo = segundo;	
	}
	
	public M getPrimeiro()
	{
		return this.primeiro;
	}
	public N getSegundo()
	{
		return this.segundo;
	}
	@Override
	public String toString()
	{
		return  "(" + this.primeiro.toString() + ", " + this.segundo.toString() + ")"; 
	}
}
