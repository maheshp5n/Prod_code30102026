package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UserNote;
import com.pratikshat.exicom.repository.UserNoteRepository;

@RestController
@RequestMapping("api/usernotes")
public class UserNoteController {
	@Autowired
	UserNoteRepository dbUserNote;
	
	@GetMapping
	List<UserNote> getUserNotes(@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		return dbUserNote.findByUserEmailId(user.getUsername());
	}
	
	@DeleteMapping("{id}")
	Map<String,Object> updateNote(@PathVariable("id")long id, @CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		dbUserNote.deleteById(id);
		return new HashMap<String,Object>();
	}
	
	@PostMapping
	List<UserNote> postNotes(@RequestBody UserNote note,@CurrentUser LoginUser user) throws IOException{
		note.setUserEmailId(user.getUsername());
		note.setCreatedDate(new Date());
		
		//System.out.println("Notes : "+note.getNotes());
		
		if(note.getNotes()==null||note.getNotes().equalsIgnoreCase(""))
			return dbUserNote.findByUserEmailId(user.getUsername());
		dbUserNote.save(note);
		return dbUserNote.findByUserEmailId(user.getUsername());
	}
}
