import java.util.Random;
import java.util.Scanner;

public class play2048
{
	private static int[][] spielfeld;
	
	public static void main( String args[] )
	{
		Scanner reader = new Scanner(System.in);
		init_game();
		print();
		
		while( true ) 
		{
			printOpts();
			char c = reader.next().charAt(0);
			
			switch( c ){
				case 'c':
				return;
				
				case 'w':
					moveUP();
					break;
					
				case 's':
					moveDOWN();
					break;
				
				case 'a':
					moveLEFT();
					break;
				
				case 'd':
					moveRIGHT();
					break;
				
				default:
					continue;
			}
			
			print();
		}
	}
	private static void printOpts()
	{
		System.out.println( "c:exit w:up s:down a:left d:right");
	}
	
	private static void init_game()
	{
		spielfeld = new int[4][4];
		
		for( int n = 0; n < 4; n++ )
		{
			for( int p = 0; p < 4; p++ )
			{
				spielfeld[n][p] = 0;
			}
		}
		
		add_rand();
		add_rand();
	}
	
	private static boolean zero_left()
	{
		for( int n = 0; n < 4; n++ )
		{
			for( int p = 0; p < 4; p++ )
			{
				if( spielfeld[n][p] == 0 )
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private static void add_rand()
	{
		if( !zero_left() )
		{
			System.out.println("Can't move that direction.");
			return;
		}
		
		int N,P;
		Random r = new Random();
		
		N = r.nextInt(4);
		P = r.nextInt(4);
		
		while( spielfeld[N][P] != 0 )
		{
			N = r.nextInt(4);
			P = r.nextInt(4);
		}
		
		if( r.nextInt(100) < 70 )
		{
			spielfeld[N][P] = 2;
		} 
		else
		{
			spielfeld[N][P] = 4;
		}
	}
	
	private static void print()
	{
		System.out.println();
		System.out.println(" -\t-\t-\t-\t");
		for( int n = 0; n < 4; n++ )
		{
			System.out.print("|");
			for( int p = 0; p < 4; p++ )
			{
				if( spielfeld[n][p] > 0 )
				{
					System.out.print( "" + spielfeld[n][p] + "\t" );
				}
				else
				{
					System.out.print( " \t" );
				}
			}
			System.out.println("|");
		}
		System.out.println(" -\t-\t-\t-\t");
	}
	
	private static void shiftLEFT()
	{
		int off;
		for( int n = 0; n < 4; n++ )
		{
			for( int p = 0; p < 4; p++ )
			{
				if( spielfeld[ n ][ p ] == 0 ){
					off = 1;
					while( p + off < 4 ){
						if( spielfeld[ n ][ p + off ] != 0 ){
							spielfeld[ n ][ p ] = spielfeld[ n ][ p + off ];
							spielfeld[ n ][ p + off ] = 0;
							break;
						}
						off++;
					}
				}
			}
		}
	}
	
	private static void shiftRIGHT()
	{
		int off;
		for( int n = 0; n < 4; n++ )
		{
			for( int p = 3; p >= 0; p-- )
			{
				if( spielfeld[ n ][ p ] == 0 ){
					off = 1;
					while( p - off >= 0 ){
						if( spielfeld[ n ][ p - off ] != 0 ){
							spielfeld[ n ][ p ] = spielfeld[ n ][ p - off ];
							spielfeld[ n ][ p - off ] = 0;
							break;
						}
						off++;
					}
				}
			}
		}
	}
	
	private static void shiftDOWN()
	{
		int off;
		for( int n = 3; n >=0; n-- )
		{
			for( int p = 0; p < 4; p++ )
			{
				if( spielfeld[ n ][ p ] == 0 ){
					off = 1;
					while( n - off >= 0 ){
						if( spielfeld[ n - off ][ p ] != 0 ){
							spielfeld[ n ][ p ] = spielfeld[ n - off][ p ];
							spielfeld[ n - off ][ p ] = 0;
							break;
						}
						off++;
					}
				}
			}
		}
	}
	
	private static void shiftUP()
	{
		int off;
		for( int n = 0; n < 4; n++ )
		{
			for( int p = 0; p < 4; p++ )
			{
				if( spielfeld[ n ][ p ] == 0 ){
					off = 1;
					while( n + off < 4 ){
						if( spielfeld[ n + off ][ p ] != 0 ){
							spielfeld[ n ][ p ] = spielfeld[ n + off ][ p ];
							spielfeld[ n + off ][ p ] = 0;
							break;
						}
						off++;
					}
				}
			}
		}
	}
	
	private static void validateHOR()
	{
		for( int n = 0; n < 4; n++ )
		{
			for( int p = 0; p < 3; p++ )
			{
				if ( spielfeld[ n ][ p ] == spielfeld[ n ][ p + 1 ] )
				{
					spielfeld[ n ][ p ] += spielfeld[ n ][ p + 1 ];
					spielfeld[ n ][ p + 1 ] = 0;
				}
			}
		}
	}
	
	private static void validateVER()
	{
		for( int n = 0; n < 3; n++ )
		{
			for( int p = 0; p < 4; p++ )
			{
				if ( spielfeld[ n ][ p ] == spielfeld[ n + 1][ p ] )
				{
					spielfeld[ n ][ p ] += spielfeld[ n + 1 ][ p ];
					spielfeld[ n + 1 ][ p ] = 0;
				}
			}
		}
	}
	
	private static void moveUP()
	{
		shiftUP();
		validateVER();
		shiftUP();
		
		add_rand();
	}
	
	private static void moveDOWN()
	{
		shiftDOWN();
		validateVER();
		shiftDOWN();
		
		add_rand();
	}
	
	private static void moveRIGHT()
	{
		shiftRIGHT();
		validateHOR();
		shiftRIGHT();
		
		add_rand();
	}
	
	private static void moveLEFT()
	{
		shiftLEFT();
		validateHOR();
		shiftLEFT();
		
		add_rand();
	}
}
