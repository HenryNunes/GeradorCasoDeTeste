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
	
	public Boolean Contains(Object obj)
	{
		if(testM(obj))
		{
			if(obj.equals(primeiro))return true;
		}
		if(testN(obj))
		{
			if(obj.equals(segundo))return true;
		}
		return false;
	}
	private Boolean testM(Object obj)
	{
		try
		{
			M temp = (M) obj;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	private Boolean testN(Object obj)
	{
		try
		{
			N temp = (N) obj;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
}
