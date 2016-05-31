/**
 * Copyright 2016 SivaLabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions 
 * and limitations under the License.
 */
package com.sto.rms.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sto.rms.entities.EmployeeSchedule;

/**
 * @author Siva
 *
 */
public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, Integer>
{
	@Query("SELECT s FROM EmployeeSchedule s WHERE SUBSTRING(s.fromDate, 1, 4) = ?1")
	public List<EmployeeSchedule> findByYear(String year);
	
	
	@Query("SELECT s FROM EmployeeSchedule s WHERE s.user.id=?1 and s.fromDate>= ?2 and s.toDate <=?3")
	public List<EmployeeSchedule> findByEmpStatusByDateRange(Integer empId, Date start, Date end);
	
	
}
