// dateUtils.js

/**
 * Format a date represented as an array [YYYY, M, D], [YYYY, MM, D], or [YYYY, MM, DD]
 * to "MM-DD-YYYY" format.
 * @param {Array} dateArray - The date array to format [YYYY, M, D], [YYYY, MM, D], or [YYYY, MM, DD].
 * @returns {string} - The formatted date string ("MM-DD-YYYY").
 */
export function formatDate(dateArray) {
    const year = dateArray[0];
    const month = dateArray[1];
    const day = dateArray[2];
  
    // Ensure month and day are zero-padded to 2 digits
    const formattedMonth = month.toString().padStart(2, '0');
    const formattedDay = day.toString().padStart(2, '0');
  
    // Format the date as "MM-DD-YYYY"
    const formattedDate = `${formattedMonth}-${formattedDay}-${year}`;
  
    return formattedDate;
  }

/**
 * Format a date-time represented as an array [YYYY, MM, DD, HH, mm]
 * to "MM-DD-YYYY hh:mm AM/PM" format.
 * @param {Array} dateTimeArray - The date-time array to format [YYYY, MM, DD, HH, mm].
 * @returns {string} - The formatted date-time string ("MM-DD-YYYY hh:mm AM/PM").
 */
export function formatDateTime(dateTimeArray) {
  const year = dateTimeArray[0];
  const month = dateTimeArray[1];
  const day = dateTimeArray[2];
  let hours = dateTimeArray[3];
  const minutes = dateTimeArray[4];

  // Determine AM or PM
  const amPm = hours >= 12 ? 'PM' : 'AM';

  // Convert hours to 12-hour format
  hours = hours % 12 || 12;

  // Ensure month, day, hours, and minutes are zero-padded to 2 digits
  const formattedMonth = month.toString().padStart(2, '0');
  const formattedDay = day.toString().padStart(2, '0');
  const formattedHours = hours.toString().padStart(2, '0');
  const formattedMinutes = minutes.toString().padStart(2, '0');

  // Format the date-time as "MM-DD-YYYY hh:mm AM/PM"
  const formattedDateTime = `${formattedMonth}-${formattedDay}-${year} ${formattedHours}:${formattedMinutes} ${amPm}`;

  return formattedDateTime;
}