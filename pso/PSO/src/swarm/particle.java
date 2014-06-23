package swarm;

import java.util.Vector;

public class particle {	
	Vector< Vector<Float> > current_location ;  // now current velocity
	Vector< Vector<Float> > current_velocity ;  // now current velocity
	Vector< Vector<Float> > previous_velocity ; //to keep inertia we require previous velocity

	float pbest ;  
	Vector< Vector<Float> > pbest_position ;  // all dimension value for pbest value
	
	/*
	 * set after all particle value calculated
	 * initialization after all particle calculated
	 */
	static float gbest ; 
	static Vector< Vector<Float> > gbest_position ;  

	static settings setting_for_all ;  // setting for all particles (inertia, c1, c2 , no of cluster , dimension ,etc)
	
	
	//When object initializes position and velocity initial values set 
	public particle(Vector< Vector<Float> > intial_values ,Vector< Vector<Float> > intial_velocities , Vector< Vector<Float> > prev_velocity_for_first_time )
	{
		current_location = new Vector<Vector<Float> >(intial_values);
		current_velocity = new Vector< Vector<Float> > (intial_velocities); 
		previous_velocity = new Vector<Vector<Float> > (prev_velocity_for_first_time);
		pbest_position = new Vector<Vector<Float> > (current_location); //copy same dimension to pbest_position bz its for first time
		/*
		 * set pbest
		 */
		swarm_based_clustering particle_cluster = new swarm_based_clustering(current_location);
		pbest = particle_cluster.get_quantization_error_value();
	}

	public void set_velocity(float rando1 ,float rando2)
	{
		Vector< Vector<Float> > temp_velocity_store = new Vector< Vector<Float> > (current_velocity) ; 
		int vecsize_curr_velocity = current_velocity.size() ;
		for (int count_index = 0 ; count_index < vecsize_curr_velocity ; count_index++)
		{
			int dimension_size = current_velocity.get(count_index).size(); 
			for (int dimencount = 0 ; dimencount < dimension_size ; dimencount++)
			{
				float prev_vel_val = previous_velocity.get(count_index).get(dimencount) ; 
				float curr_vel_val = current_velocity.get(count_index).get(dimencount) ; 
				float pbest_val = pbest_position.get(count_index).get(dimencount);
				float gbest_val = gbest_position.get(count_index).get(dimencount);
				float random1 = rando1 ; 
				float random2 = rando2 ;
				float now_vel=velocity_cal_formula(prev_vel_val,curr_vel_val,pbest_val, gbest_val, random1 ,random2);
				current_velocity.get(count_index).set(dimencount, now_vel);
			}
		}
		// set previous velocity as temp velocity  // above calculation calculates latest velocity 
		previous_velocity = new Vector<Vector<Float>>(temp_velocity_store);
	}

	public void set_position()
	{
		int curr_position_size = current_location.size() ; 
		for (int count_position = 0 ; count_position < curr_position_size ; count_position++)
		{
			int get_dim_size = current_location.get(count_position).size() ; 
			for (int dime_counter = 0 ; dime_counter < get_dim_size ; dime_counter++)
			{
				float new_position = (
						current_location.get(count_position).get(dime_counter)+
						current_velocity.get(count_position).get(dime_counter));
				current_location.get(count_position).set(dime_counter, new_position);
			}
		}
	} 
	public void set_pbest()
	{
		float temp_pbest ; 
		swarm_based_clustering particle_cluster = new swarm_based_clustering(current_location);
		temp_pbest = particle_cluster.get_quantization_error_value();
		if (temp_pbest < pbest)
		{
			pbest = temp_pbest ; 
			pbest_position = new Vector<Vector<Float> > (current_location);
		}
	}
	public float velocity_cal_formula(float pre_vel,float curr_val, float pbest_val , float gbest_val , float random1 , float random2)
	{
		float curr_dimension_velocity = (
		setting_for_all.w *  pre_vel + 
		setting_for_all.c1 * random1 * (gbest_val - curr_val) +
		setting_for_all.c2 * random2* (pbest_val -  curr_val)
		); 
		return curr_dimension_velocity ;
	}
	
	public float position_cal_formula(float pre_val , float curr_vel)
	{
		float curr_dimension_position = pre_val + curr_vel ; 
		return curr_dimension_position ; 
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
