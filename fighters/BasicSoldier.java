package fighters;

import framework.BattleField;
import framework.Random131;

public class BasicSoldier {

	
	public final static int INITIAL_HEALTH = 15;
	public final static int ARMOR = 15;
	public final static int STRENGTH = 35;
	public final static int SKILL = 35;
	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int UP_AND_RIGHT = 4;
	public final static int DOWN_AND_RIGHT = 5;
	public final static int DOWN_AND_LEFT = 6;
	public final static int UP_AND_LEFT = 7;
	public final static int NEUTRAL = -1;

	public final BattleField grid;
	public int row;
	public int col;
	public int health;
	public final int team;
	public int enemyTeam; 
	
	public BasicSoldier (BattleField gridIn, int teamIn, int rowIn, int colIn) {
		grid = gridIn;
		team = teamIn;
		row = rowIn;
		col = colIn;
		health = INITIAL_HEALTH;
	}
	
	
		public void enemy() {
	if (team == BattleField.BLUE_TEAM) {
		enemyTeam = BattleField.RED_TEAM;
	} else if (team == BattleField.RED_TEAM) {
		enemyTeam = BattleField.BLUE_TEAM;
	}
		
	
			
		}
		public boolean canMove() {
			if (grid.get(row - 1 , col) == BattleField.EMPTY ||
				grid.get(row + 1 , col) == BattleField.EMPTY ||
				grid.get(row, col - 1) == BattleField.EMPTY ||
				grid.get(row, col + 1) == BattleField.EMPTY) { //can move up because empty
				return true;
			
			} else {
				
			return false;
		}
	}
		
		public boolean isEnemyRed(BattleField grid, int row, int col) {
			if(grid.get(row , col) == BattleField.RED_TEAM) {
					return true;
				 } else {
					return false;
				 }
			}
		
		public boolean isEnemyBlue(BattleField grid, int row, int col) {
			if(grid.get(row, col) == BattleField.BLUE_TEAM) {
				return true;
			} else {
				return false;
			}
		}
		
		public int numberOfEnemiesRemaining() {
			if (team == BattleField.BLUE_TEAM) {
				enemyTeam = BattleField.RED_TEAM;
				//count the number of enemies on the red team
				int counter = 0;
				for (int row  = 0; row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (isEnemyRed(grid, row, col)){
				            counter++;
				        }
				    }
				}
				return counter;

			} else {
				enemyTeam = BattleField.BLUE_TEAM;
				// count the number of enemies in the blue team 
				int counter = 0;
				for (int row  = 0; row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (isEnemyBlue(grid, row, col)){
				            counter++;
				        }
				    }
				}
				return counter;
			}
		}
		
		public int getDistance(int destinationRow, int destinationCol) {
			//given the destination that the soldier needs to reach
			//find the distance from the given to where the soldier is 
			return Math.abs(row - destinationRow) + Math.abs(col - destinationCol);
		}
		
		public int getDirection(int destinationRow, int destinationCol) {
			if (destinationRow == row && destinationCol < col) {
					return LEFT;
					
			} if (destinationRow == row && destinationCol > col) {
					return RIGHT;
					
			} if (destinationCol == col && destinationRow < row) {
					return UP;
					
			} if (destinationCol == col && destinationRow > row) {
					return DOWN;
					
			} if (destinationCol < col && destinationRow < row) {
					return UP_AND_LEFT;
					
			} if (destinationCol < col && destinationRow > row) {
					return DOWN_AND_LEFT;
					
			} if (destinationCol > col && destinationRow < row) {
					return UP_AND_RIGHT;
					
			} if (destinationCol > col && destinationRow > row) {
					return DOWN_AND_RIGHT; 
					
			} if (destinationRow == row && destinationCol == col) {
					return NEUTRAL;
			}
			return NEUTRAL;
		}
		
		public int getDirectionOfNearestFriend() {
			int friendDistance = grid.getRows() + grid.getCols() - 2;
			int friendRow = row;
			int friendCol = col;
			if (team == BattleField.BLUE_TEAM) { 
				for (int row  = 0; row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (grid.get(row, col) == BattleField.BLUE_TEAM && 
				        		getDistance(row, col) <= friendDistance && 
				        		getDistance(row, col) > 0) {
				            friendDistance = getDistance(row, col);
				            friendRow = row;
				            friendCol = col;
				        }
				    }
				}
			} else if(team == BattleField.RED_TEAM) {
				for (int row  = 0; row < grid.getRows(); row++) {
					for (int col = 0; col < grid.getCols(); col++) {
					    if (grid.get(row, col) == BattleField.RED_TEAM && 
					    		getDistance(row, col) <= friendDistance && 
					        	getDistance(row, col) > 0) {
					       friendDistance = getDistance(row, col);
					       friendRow = row;
					       friendCol = col;
					        }
					    }
					}	
				}
				return getDirection(friendRow, friendCol);
			}
		
		public int countNearbyFriends(int radius) {
			int friendDistance = radius;
			int friends = -1;
			if (team == BattleField.BLUE_TEAM) {
				for (int row  = 0 ;row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (grid.get(row, col) == BattleField.BLUE_TEAM && 
				        		getDistance(row, col) <= friendDistance){
				            friends+=1;
				        }
				    }
				}
			} else if (team == BattleField.RED_TEAM) {
				// count the number of friends on the red team 
				for (int row  = 0 ;row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (grid.get(row, col) == BattleField.RED_TEAM && 
				        		getDistance(row, col) <= friendDistance){
				            friends+=1;
				        }
				    }
				}
			}
			return friends;
		}
		
		public int getDirectionOfNearestEnemy(int radius) {
			int enemyDistance = radius;
			int enemyRow = row;
			int enemyCol = col;
			if (team == BattleField.BLUE_TEAM) {
				for (int row  = 0 ;row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (grid.get(row, col) == BattleField.RED_TEAM &&
				        		getDistance(row, col) <= enemyDistance) {
				        	enemyDistance = getDistance(row, col);
				        	enemyRow = row;
				        	enemyCol = col;
				        }
				    }
				}
			} else if (team == BattleField.RED_TEAM){
				for (int row  = 0 ;row < grid.getRows(); row++) {
				    for (int col = 0; col < grid.getCols(); col++) {
				        if (grid.get(row, col) == BattleField.BLUE_TEAM &&
				        		getDistance(row, col) <= enemyDistance) {
				        	enemyDistance = getDistance(row, col);
				        	enemyRow = row;
				        	enemyCol = col;
				        } 
				    }
				}	 
			}
			return getDirection(enemyRow, enemyCol);
		}
		
		public void performMyTurn() {
				if (team == BattleField.BLUE_TEAM) {
					if (grid.get(row-1, col) == BattleField.RED_TEAM) {
						grid.attack(row-1,col);
					}
					else if (grid.get(row+1, col) == BattleField.RED_TEAM) {
						grid.attack(row+1, col);
					}
					else if (grid.get(row, col-1) == BattleField.RED_TEAM) {
						grid.attack(row, col-1);
					}
					else if (grid.get(row,  col+1) == BattleField.RED_TEAM) {
						grid.attack(row, col+1);
					}
					else if (grid.get(row,  col+1) == BattleField.EMPTY) {
						col = col + 1;
					}
					else if (grid.get(row,  col-1) == BattleField.EMPTY) {
						col = col - 1;
					}
					else if (grid.get(row+1,  col) == BattleField.EMPTY) {
						row = row + 1;
					}
					else if (grid.get(row-1, col) == BattleField.EMPTY) {
						row = row - 1;
					}
				}
				else if (team == BattleField.RED_TEAM) {
					if (grid.get(row-1, col) == BattleField.BLUE_TEAM) {
						grid.attack(row-1,col);
					}
					else if (grid.get(row+1, col) == BattleField.BLUE_TEAM) {
						grid.attack(row+1, col);
					}
					else if (grid.get(row, col-1) == BattleField.BLUE_TEAM) {
						grid.attack(row, col-1);
					}
					else if (grid.get(row,  col+1) == BattleField.BLUE_TEAM) {
						grid.attack(row, col+1);
					}
					else if (grid.get(row,  col+1) == BattleField.EMPTY) {
						col = col + 1;
					}
					else if (grid.get(row,  col-1) == BattleField.EMPTY) {
						col = col - 1;
					}
					else if (grid.get(row+1,  col) == BattleField.EMPTY) {
						row = row + 1;
					}
					else if (grid.get(row-1, col) == BattleField.EMPTY) {
						row = row - 1;
					}
				}	
			}
		
	
}
