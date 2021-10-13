# Compose Date Picker - Select month and year
[![](https://jitpack.io/v/DogusTeknoloji/compose-date-picker.svg)](https://jitpack.io/#DogusTeknoloji/compose-date-picker)

Compose Date Picker tries to offer you the year and month pickers which you can customize for your requirements. The library complately written with **Jetpack Compose**.

Support for Android 5.0 (API level 21) and up.

**Screenshots**
| ![enter image description here](https://github.com/DogusTeknoloji/compose-date-picker/blob/main/screenshots/ss%20%281%29.png?raw=true) | ![enter image description here](https://github.com/DogusTeknoloji/compose-date-picker/blob/main/screenshots/ss%20%282%29.png?raw=true) |
|--|--|
| ![enter image description here](https://github.com/DogusTeknoloji/compose-date-picker/blob/main/screenshots/ss%20%283%29.png?raw=true) |  |

## Implementation
Add it in your root build.gradle at the end of repositories:

```css
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
The easiest way to add the Compose Date Picker library to your project is by adding it as a dependency to your  `build.gradle`
```css
dependencies {
	        implementation 'com.github.DogusTeknoloji:compose-date-picker:1.0.1'
	}
```
## Usage Compose Date Picker

    ComposeCalendar(  
      minDate: Date? = null,  // Set min selectable date
      maxDate: Date? = null,  // Set max selectable date
      locale: Locale = Locale.getDefault(),  // Set locale for localization
      title: String = "",  // Set title 
      listener: SelectDateListener, // Set Listener for selected date
      showOnlyMonth: Boolean = false,  // Display only month picker
      showOnlyYear: Boolean = false,  // Display only year picker
      themeColor:Color = Color(0xFF614FF0), // Set picker color 
      negativeButtonTitle:String = "CANCEL",  // Set negative button text
      positiveButtonTitle:String = "OK"  // Set positive button text
    )
**Listener**

    interface SelectDateListener {  
        fun onDateSelected(date: Date)  
        fun onCanceled()  
    }
**Compose Sample**

    val calendar = Calendar.getInstance()  
    calendar.set(Calendar.YEAR, 2010)  
    calendar.set(Calendar.MONTH, 6)  
    val calendarMax = Calendar.getInstance()  
    calendarMax.set(Calendar.YEAR, 2032)  
    calendarMax.set(Calendar.MONTH, 9)
    
    Box(Modifier  
        .fillMaxSize()  
        .background(color = Color.Gray), contentAlignment = Alignment.Center) {  
       
	      ComposeCalendar(minDate = calendar.time,  
	      maxDate = calendarMax.time,  
	      locale = Locale("en"),  
	      title = "Select Date",  
	      listener = object : SelectDateListener {  
	                    override fun onDateSelected(date: Date) {  
	                        Log.i("DATE", date.toString())  
	                    }  
	      
	                    override fun onCanceled() {  
	                        setOpen(false)  
	                    }  
	                })  
	        }
**XML Layout Sample**
	
    //XML Layout
    
    <androidx.compose.ui.platform.ComposeView
      android:id="@+id/composeDatePickerView"
      android:layout_width="match_parent"
      android:layout_height="match_parent" />
      
      
	//Activity-Fragment
	
    binding.composeDatePickerView.apply{
	    setContent{
		    ComposeCalendar(minDate = calendar.time,  
		      maxDate = calendarMax.time,  
		      locale = Locale("en"),  
		      title = "Select Date",  
		      listener = object : SelectDateListener {  
		                    override fun onDateSelected(date: Date) {  
		                        Log.i("DATE", date.toString())  
		                    }  
		      
		                    override fun onCanceled() {  
		                        setOpen(false)  
		                    }  
		                })  
	    }
	}

Design inspired by https://github.com/premkumarroyal/MonthAndYearPicker 
