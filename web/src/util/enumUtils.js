// enumUtils.js

// Function to get the value for a given key in an enum
export function getValueFromEnum(key, enumObject) {
  return enumObject[key] || key; // If key not found, return the key itself
}
