// dateUtils.js

/**
 * Format a date represented as an array [YYYY, M, D], [YYYY, MM, D], or [YYYY, MM, DD]
 * to "MM-DD-YYYY" format.
 * @param {Array} dateArray - The date array to format [YYYY, M, D], [YYYY, MM, D], or [YYYY, MM, DD].
 * @returns {string} - The formatted date string ("MM-DD-YYYY").
 */
export function formatDateToMMDDYYYY(dateArray) {
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