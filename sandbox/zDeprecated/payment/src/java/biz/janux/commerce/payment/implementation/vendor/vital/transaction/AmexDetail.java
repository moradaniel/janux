package biz.janux.commerce.payment.implementation.vendor.vital.transaction;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
import biz.janux.commerce.payment.util.Constants;
import biz.janux.commerce.payment.util.DataFormattingUtil;

public class AmexDetail extends FormatDetails {
		/**
		 * Logger for this class
		 */
		private static final Logger logger = Logger.getLogger(AmexDetail.class);

		// Section 4.41
		// AMEX SETTLEMENT ONLY
		// TO BE SENT IN YYMMDD FORMAT
		Date checkOutDate;

		// Section 4.188
		// AMEX SETTLEMENT ONLY
		// CAN NOT BE "00"
		int duration;

		// Section 4.172
		// AMEX SETTLEMENT ONLY
		// NUMERIC LENGTH 12
		// "This field must contain at least one non-zero value."
		BigDecimal rate;

		public AmexDetail(VitalAuthorizationResponseModel response, Date checkOutDate,
				int duration, BigDecimal rate , DetailType type) {
			super(response , type);

			if (logger.isDebugEnabled()) {
				logger.debug("AmexDetail(response=" + response + ", checkOutDate="
						+ checkOutDate + ", duration=" + duration + ", rate="
						+ rate + ")");
			}

			setRecordType(Constants.RECORD_TYPE_AMEX_DETAIL);

			// This is required for AMEX, the default NO_SHOW_INDICATOR value
			// is NOT Valid for AMEX!
			setNoShowIndicator(Constants.SPECIAL_PROGRAM_INDICATOR_OTHER);

			this.checkOutDate = checkOutDate;
			this.duration = duration;
			this.rate = rate;
		}

		public String formatDetailContents() {

			if (logger.isDebugEnabled()) {
				logger.debug("formatDetailContents()");
			}

			StringBuffer buffer = new StringBuffer();

			// Get BASE Detail Message
			buffer.append(super.formatDetailContents());

			// Charge Type
			// USING HOTEL CONSTANT FOR NOW
			buffer.append(Constants.CHARGE_TYPE_HOTEL);

			// CHECK OUT DATE
			// TODO: We've had problems where checkout date is
			// used as some sort of transaction date?
			// TODO: If we support non CHARGE_TYPE_HOTEL transactions,
			// this must be 000000
			buffer.append(DataFormattingUtil.formatDateToYYMMDD(checkOutDate));

			// Duration
			// CAN NOT BE 0 (unless !CHARGE_TYPE_HOTEL)
			if (duration == 0)
				duration = 1;

			if (duration > 99)
				duration = 99;

			buffer.append(DataFormattingUtil.rightJustifyZero(String.valueOf(duration), 2));

			// RATE CAN NOT BE 0.00, or as spec says:
			// "This field must contain at least one non-zero value."
			// If rate is less than or equal to 0.00, send $1.00
			if (rate.compareTo(new BigDecimal(0.00)) <= 0)
				rate = new BigDecimal(1.00);

			buffer.append(DataFormattingUtil.rightJustifyZero(DataFormattingUtil.formatAmount(rate), 12));

			return buffer.toString();
		}
	}


	
	

