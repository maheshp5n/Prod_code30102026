package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.ApprovalSteps;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ApprovalRequest;
import com.pratikshat.exicom.model.request.ApprovalRuleRequest;
import com.pratikshat.exicom.model.request.ApprovalStepRequest;
import com.pratikshat.exicom.model.response.ApprovalResponse;
import com.pratikshat.exicom.model.response.ApprovalStepsResponse;
import com.pratikshat.exicom.model.response.SimpleResponseMessage;
import com.pratikshat.exicom.repository.ApprovalRepository;
import com.pratikshat.exicom.repository.ApprovalStepsRepository;

@RestController
@RequestMapping("api/approval")
public class ApprovalController {

	@Autowired
	ApprovalRepository dbApproval;
	
	@Autowired
	ApprovalStepsRepository dbApprovalStep;

	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	@GetMapping
	public Page<com.pratikshat.exicom.model.response.ApprovalResponse> getAll(Pageable page) {
		return dbApproval.getAll(page);
	}

	// @GetMapping("recentvisit")
	// public Page<AnnexureResponse> getReceintVisist(@CurrentUser LoginUser
	// user,Pageable page){
	// return dbAnnexure.getAnnexureRecentVisit(user.getUsername(), page);
	// }
	@GetMapping("{approvalId}")
	public com.pratikshat.exicom.model.response.ApprovalResponse getByApprovalResponse(
			@PathVariable("approvalId") Long approvalId, @CurrentUser LoginUser user) {
		ApprovalResponse approvalResponse = dbApproval.getByApprovalId(approvalId);
		return approvalResponse;
	}

	@PostMapping
	public ApprovalResponse createApproval(@RequestBody ApprovalRuleRequest request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Approval", "createRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (request != null) {
			if (request.getObjectClass() != null && !request.getObjectClass().equalsIgnoreCase("")) {
				if (request.getEntryCriteria() != null && !request.getEntryCriteria().equalsIgnoreCase("")) {
					if (request.getInitialSubmitterList() != null && request.getInitialSubmitterList().size() > 0) {
						if (request.getDescriptions() != null && !request.getDescriptions().equalsIgnoreCase("")) {
							if (request.getBusinessDomainId() != 0) {
								if (request.getApprovalStages() != 0) {
									try {
										ApprovalRuleRequest approvalRuleRequest = dbApproval.createApprovalRule(request,user);
										return dbApproval.getByApprovalId(approvalRuleRequest.getId());
									} catch (BadRequestException ex) {
										httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
										return null;
									} catch (UnauthoriseException ex) {
										httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
										return null;
									} catch (Exception ex) {
										httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
										return null;
									}
								} else {
									httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Approval stages is wrong");
									return null;
								}
							} else {
								httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Business domain is wrong");
								return null;
							}
						} else {
							httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Description is missing");
							return null;
						}
					} else {
						httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Initial submitter is missing");
						return null;
					}
				} else {
					httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Entry critaria is missing");
					return null;
				}
			} else {
				httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Object class name is missing");
				return null;
			}
		} else {
			httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Request paramaters null");
			return null;
		}
	}

	@PutMapping
	public ApprovalResponse updateApproval(@RequestBody ApprovalRuleRequest request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Approval", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (request != null) {
			if (request.getId() != 0) {
				if (request.getObjectClass() != null && !request.getObjectClass().equalsIgnoreCase("")) {
					if (request.getEntryCriteria() != null && !request.getEntryCriteria().equalsIgnoreCase("")) {
						if (request.getInitialSubmitterList() != null && request.getInitialSubmitterList().size() > 0) {
							if (request.getDescriptions() != null && !request.getDescriptions().equalsIgnoreCase("")) {
								if (request.getBusinessDomainId() != 0) {
									if (request.getApprovalStages() != 0) {
										try {
											ApprovalRuleRequest approvalRuleRequest = dbApproval.updateApprovalRule(request, user);
											return dbApproval.getByApprovalId(approvalRuleRequest.getId());
										} catch (BadRequestException ex) {
											httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
											return null;
										} catch (UnauthoriseException ex) {
											httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
											return null;
										} catch (Exception ex) {
											httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
											return null;
										}
									} else {
										httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Approval stages is wrong");
										return null;
									}
								} else {
									httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Business domain is wrong");
									return null;
								}
							} else {
								httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Description is missing");
								return null;
							}
						} else {
							httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Initial submitter is missing");
							return null;
						}
					} else {
						httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Entry critaria is missing");
						return null;
					}
				} else {
					httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Object class name is missing");
					return null;
				}
			} else {
				httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Approval id wrong");
				return null;
			}
		} else {
			httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Request paramaters null");
			return null;
		}
	}

	@PostMapping("step")
	public ApprovalStepsResponse createApprovalStep(@RequestBody ApprovalStepRequest request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Approval", "createRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (request != null) {
			if (request.getObjectFieldName() != null && !request.getObjectFieldName().equalsIgnoreCase("")) {
				if (request.getApprovedBy() != null && !request.getApprovedBy().equalsIgnoreCase("")) {
					if (request.getOnApprovedValue() != null && !request.getOnApprovedValue().equalsIgnoreCase("")) {
						if (request.getOnRejectedValue() != null && !request.getOnRejectedValue().equalsIgnoreCase("")) {
							if (request.getStepName() != null && !request.getStepName().equalsIgnoreCase("")) {
								if (request.getApprovedStage() != 0) {
									if (request.getApprovalId() != 0) {
										try {
											ApprovalStepRequest approvalStepRequest = dbApproval.createApprovalStep(request, user);
											return dbApprovalStep.getApprovalStepsById(approvalStepRequest.getId());
										} catch (BadRequestException ex) {
											httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
											return null;
										} catch (UnauthoriseException ex) {
											httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
											return null;
										} catch (Exception ex) {
											httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
											return null;
										}
									} else {
										httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Approval id is wrong");
										return null;
									}
								} else {
									httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Approval stages is wrong");
									return null;
								}
							} else {
								httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Step name is missing");
								return null;
							}
						} else {
							httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "on rejected value is missing");
							return null;
						}
					} else {
						httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "on approved value is missing");
						return null;
					}
				} else {
					httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Approved by is missing");
					return null;
				}
			} else {
				httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Object field is missing");
				return null;
			}
		} else {
			httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Request paramaters null");
			return null;
		}
	}

	@PutMapping("step")
	public ApprovalStepsResponse updateApprovalStep(@RequestBody ApprovalStepRequest request, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException {

		if (!dbProfile.hasObjectAccess(user.getUsername(), "Approval", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}

		if (request != null) {
			if (request.getId() != 0) {
					if (request.getObjectFieldName() != null && !request.getObjectFieldName().equalsIgnoreCase("")) {
						if (request.getApprovedBy() != null && !request.getApprovedBy().equalsIgnoreCase("")) {
							if (request.getOnApprovedValue() != null && !request.getOnApprovedValue().equalsIgnoreCase("")) {
								if (request.getOnRejectedValue() != null && !request.getOnRejectedValue().equalsIgnoreCase("")) {
									if (request.getStepName() != null && !request.getStepName().equalsIgnoreCase("")) {
										if (request.getApprovedStage() != 0) {
											if (request.getApprovalId() != 0) {
												try {
													ApprovalStepRequest approvalStepRequest = dbApproval.updateApprovalStep(request, user);
													return dbApprovalStep.getApprovalStepsById(approvalStepRequest.getId());
												} catch (BadRequestException ex) {
													httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,ex.getMessage());
													return null;
												} catch (UnauthoriseException ex) {
													httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,ex.getMessage());
													return null;
												} catch (Exception ex) {
													httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
													return null;
												}
											} else {
												httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Approval id is wrong");
												return null;
											}
										} else {
											httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Approval stages is wrong");
											return null;
										}
									} else {
										httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"Step name is missing");
										return null;
									}
								} else {
									httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"on rejected value is missing");
									return null;
								}
							} else {
								httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST,"on approved value is missing");
								return null;
							}
						} else {
							httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Approved by is missing");
							return null;
						}
					} else {
						httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Object field is missing");
						return null;
					}
			} else {
				httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Approval step id wrong");
				return null;
			}
		} else {
			httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Request paramaters null");
			return null;
		}
	}

	
	@DeleteMapping("steps/{id}")
	public SimpleResponseMessage updateApprovalStep(@PathVariable("id") long id, @CurrentUser LoginUser user,
			HttpServletResponse httpServletResponse) throws IOException {
		
		if (!dbProfile.hasObjectAccess(user.getUsername(), "Approval", "editRights")) {
			httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		
		if(id!=0) {
			try {
				dbApprovalStep.deleteById(id);
				return new SimpleResponseMessage("Deleted successfully");
			} catch (Exception ex) {
				httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
				return null;
			}
		}else {
			httpServletResponse.sendError(HttpStatus.SC_BAD_REQUEST, "Approval step id wrong");
			return null;	
		}
	}
	
	
	@GetMapping("steps/{approvalStepId}")
	public com.pratikshat.exicom.model.response.ApprovalStepsResponse getApprovalStepsByApprovalStepId(
			@PathVariable("approvalStepId") Long approvalStepId, @CurrentUser LoginUser user) {
		
		return  dbApprovalStep.getApprovalStepsById(approvalStepId);
		
	}
	
	
	@GetMapping("stepslist/{approvalId}")
	public List<com.pratikshat.exicom.model.response.ApprovalStepsResponse> getStepsByApprovalId(
			@PathVariable("approvalId") Long approvalId, @CurrentUser LoginUser user) {
		
		return  dbApprovalStep.getApprovalStepsByApprovalId(approvalId);
		
	}
}
