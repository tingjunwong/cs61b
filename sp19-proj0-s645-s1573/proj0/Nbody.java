public class NBody
{
	public static double readRadius(filename)
	{
		In in = new In(filename);
		int N=in.readInt();
		int R=in.readDouble();
	}

	public static Body[] readBodies(filename)
	{
		In in = new In(filename);

		int N=in.readInt();
		int R=in.readDouble();

		Body[] someArray = new Body[readint()];

		if(someArray.length==0) 
		{
			System.out.println("please supply a planet as a command line argument");
		}
		else
		{
			int i=0;
			while(i<someArray.length)
			{
				double someArray[i].xxPos=in.readDouble();
		        double someArray[i].yyPos=in.readDouble();
		        double someArray[i].xxVel=in.readDouble();
		        double someArray[i].yyVel=in.readDouble();
		        double someArray[i].mass=in.readDouble();
		        String someArray[i].imgFilename=in.readString();
		        someArray[i]=new Body(xp,yp,xv,yv,mass,name);
		        i=i+1;
		    }
		}
		return someArray;
	}

	public static void main()
	{
		double T;
		double dt;
		String filename;
		readRadius();
		readBodies();
	}

}
