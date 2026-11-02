import { Injectable } from "@angular/core";
@Injectable()
export class FieldValidation {

	static isEmpty(value: string): boolean {
		if (value != null) {
			if ((value.length > 0)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}


	static isValidName(name: string): boolean {
		if (name != null) {
			if ((name.length > 2 && name.length < 200)) {
				const namePattern = '[a-zA-Z]';
				if (name.match(namePattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}

	}


	static isValidAddress(address: string): boolean {
		if ((address.length > 2 && address.length < 512)) {
			const addressPattern = '[a-zA-Z0-9_-/,]';
			if (address.match(addressPattern)) {
				return true;
			}
			else {
				return false;
			}
		} else {
			return false;
		}
	}


	static isValidAddressMandatory(address: string): boolean {
		if (address != null) {
			if ((address.length > 2 && address.length < 512)) {
				const addressPattern = '[a-zA-Z0-9_-/,]';
				if (address.match(addressPattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	static isValidCurrency(amount: string): boolean {
		const amountPattern = '[0-9.]';
		// /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:(\.|,)\d+)?$/
		if (amount.match(amountPattern)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	static isValidCurrencyForUndifined(amount: string): boolean {
		const amountPattern = '[0-9.]';
		if ( amount!==undefined && amount.match(amountPattern)) {
			return true;
		}
		else {
			return false;
		}
	}
  
	
	static isValidCurrencyMandatory(amount: string): boolean {
		if (amount != null) {
			if (amount.length < 8) {
				const amountPattern = '[0-9.]';
				if (amount.match(amountPattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}

		} else {
			return false;
		}

	}




	static isValidEmail(email: string): boolean {
		// [a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+
		let emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (email.match(emailPattern))
			return true;
		else
			return false;


	}

	static isValidEmailMandatory(email: string): boolean {
		if (email != null) {
			let emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			if (email.match(emailPattern))
				return true;
			else
				return false;
		} else {
			return false;
		}

	}


	static isValidMobileNumber(mobileNumber: string): boolean {
		if (mobileNumber == undefined || mobileNumber == null) {
			return false;
		}
		if (mobileNumber.startsWith('0') || mobileNumber.startsWith('2') || mobileNumber.startsWith('3') || mobileNumber.startsWith('4') || mobileNumber.startsWith('1')) {
			return false;
		}
		const phonePattern = '^((\\+91-?)|0)?[0-9]{10}$';

		if (mobileNumber.match(phonePattern)) {
			return true;
		}
		else {
			return false;
		}

	}


	static isValidMobileNumberMandatory(mobileNumber: string): boolean {
		if (mobileNumber != null) {
			const phonePattern = '[0-9+-]';
			if (mobileNumber.match(phonePattern)) {
				return true;
			}
			else {
				return false;
			}
		} else {
			return false;
		}

	}


	static isValidTelephoneNumber(telephoneNumber: string): boolean {

		const phonePattern = '^((\\+91-?)|0)?[0-9]{10}$';
		if (telephoneNumber.match(phonePattern)) {
			return true;
		}
		else {
			return false;
		}

	}


	static isValidTelephoneNumberMandatory(telephoneNumber: string): boolean {
		if (telephoneNumber != null) {
			const phonePattern = '[0-9+-]';
			if (telephoneNumber.match(phonePattern)) {
				return true;
			}
			else {
				return false;
			}
		} else {
			return false;
		}

	}

	static isValidOTPFourDigit(otp: string): boolean {
		if (otp != null) {
			if (otp.length == 4) {
				const otpPattern = '[0-9]';
				if (otp.match(otpPattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	static isValidOTPSixDigit(otp: string): boolean {
		if (otp != null) {
			if (otp.length == 6) {
				const otpPattern = '[0-9]';
				if (otp.match(otpPattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	static isValidAadharNumber(aadharNumber: string): boolean {

		if (aadharNumber.length == 12) {
			const aadharPattern = '[0-9]';
			if (aadharNumber.match(aadharPattern)) {
				return true;
			}
			else {
				return false;
			}
		} else {
			return false;
		}

	}


	static isValidAadharNumberMandatory(aadharNumber: string): boolean {
		if (aadharNumber != null) {
			if (aadharNumber.length == 12) {
				const aadharPattern = '[0-9]';
				if (aadharNumber.match(aadharPattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	static isValidPostalCode(postalCode: string): boolean {
		let postalPattern = "^((\\+91-?)|0)?[0-9]{6}$";
		if (postalCode.match(postalPattern))
			return true;
		else
			return false;
	}

	static isValidPostalCodeMendatory(postalCode: string): boolean {
		if (postalCode != null) {
			if (postalCode.length == 6) {
				const postalPattern = '[0-9]';
				if (postalCode.match(postalPattern)) {
					return true;
				}
				else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	static isValidNumber(numberCheck: any): boolean {
		let numberPattern = /^[0-9]+$/;
		if (numberCheck !== null && (numberCheck !== undefined && numberCheck.length > 0)) {
			if (numberPattern.test(numberCheck)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}

	}

	static isShippingPostalCode(postalCode: string): boolean {
		let postalPattern = "^((\\+91-?)|0)?[0-9]{6}$";
		if (postalCode != null && (postalCode !=undefined && postalCode.length > 0 ))
			if (postalCode.match(postalPattern))
				return true;
			else
				return false;
		else {
			return true;
		}

	}

	static isValidPostaCodeNotStartZero(postalCode :string):boolean{
		let postalPattern = "^[1-9]{1}[0-9]{2}\\s{0, 1}[0-9]{3}$";
		if (postalCode != null && (postalCode !=undefined && postalCode.length > 0 ))
			if (postalCode.match(postalPattern))
				return true;
			else
				return false;
		else {
			return true;
		}

	}
	static isLetter(str:any):boolean {
		
		if(str.match(/[a-z]/i))
		{
		  return true;
		}
		else{
		  return false;
		}
	  }
}
