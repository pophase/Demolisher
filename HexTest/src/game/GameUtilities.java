package game;

import java.util.ArrayList;
import java.util.List;

public class GameUtilities<X,Y> {

	
	
	
	
	public static List<BasicAbility> findAbilities(List<BasicAbility> abilityList, Class<?> toFind)
	{
		
		List<BasicAbility> resultList = new ArrayList<BasicAbility>();
		if(abilityList != null)
		for(BasicAbility ab : abilityList)
		{
		
				resultList.add(ab);
		}
		return null;
		
		
		
	}
}
