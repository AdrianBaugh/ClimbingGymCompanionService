// dateUtils.js

// Helper function to format ZonedDateTime timestamp to MM-DD-YYYY
export function formatDate(timestamp) {
  const date = new Date(timestamp * 1000); // Convert seconds to milliseconds
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Intl.DateTimeFormat('en-US', options).format(date);
}

// Helper function to format ZonedDateTime timestamp to MM-DD-YYYY hh:mm AM/PM
export function formatDateTime(timestamp) {
  const date = new Date(timestamp * 1000); // Convert seconds to milliseconds
  const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: 'numeric', minute: 'numeric', hour12: true };
  return new Intl.DateTimeFormat('en-US', options).format(date);
}