# MavericksApp
This project is for Mavericks Challenge

Q#1 What is the major difference between an abstract class and an interface?

1. Abstract class can have abstract and non abstract methods whereas interface cannot 
  have non abstract methods and any class implements the interface must implement all the methods of implemented interface.
2. java does not support multiple inheritance so you can extends only one abstract class
  but you can implement multiple interfaces
3. Abstract class can have a constructor whereas interface cannot have that.
4. Abstract class methods can have public ,protected,private and default
  modifier, Interface methods are by default public we can not use any other access modifier with it.
5. Abstract class can have main method and we can run it whereas Interface do not have main method so we can not run it.


Q#2 why is Java 7’s class inheritance flawed?

The use of inheritance in Java becomes complicated when one or more inner classes inherit from superclass. 
There is a potential reintroduction of the problem that encountered by multiple inheritance, 
such as the potential confusion of polymorphic implementation of methods. 
The use of inheritance in Java inner classes potentially leads to the difficulty of program comprehension and maintenance. 
Inheritance can be used at inner and outer class. When inheritance is found at inner class, 
there is a likelihood of cyclic inheritance which is undetected by Java compiler. 
In Java, cyclic inheritance is not allowed if a class, be it inner or outer, attempts to inherits from itself. 
Cyclic inheritance is absurd conceptually because it implies that a class is its superclass and subclass at the same time.
class A extends A{ 
  class InnerA{ }
}
class B{
  class InnerB extends InnerB{ } 
}
class C extends C.InnerC{ 
  class InnerC{ }
}
Source: https://arxiv.org/ftp/arxiv/papers/1301/1301.6260.pdf


Q#3 What are the major differences between Activities and Fragments ?

1. Activity can exist without fragment in it, whereas fragment cannot exist without activity, 
  it should be always part of an activity.
2. lifecycle of activity is managed by OS whereas life cycle of fragment is managed by activity.
3. Fragments are light weight as compared to activities.
4. one activity can contains multiple fragments in itself depending upon layout and screen sizes.
5. A fragment can be reused in multiple activities, so it acts like a reusable
  component in activities.


Q#4 When using Fragments, how do you communicate back to their hosting Activity?

Best and easiest way to communicate between your activity and fragments is using interfaces. 
we define an interface inside a given fragment A and implement that interface in activity. 
and than we can do anything we want in the method it overrides. 
you must have to call the abstract method from your fragment and cast it to your activity.
It should catch a ClassCastException if not done correctly. 
i used the same example in given App challenge please review that.

Exapmle
//This should be done in your fragment
public static interface OnCompleteListener {
  public abstract void onComplete(int result, User user);
}
private OnCompleteListener mListener;
// make sure the Activity implemented it @Override
public void onAttach(Context activity) { 
  super.onAttach(activity);
try {
  this.mListener = (OnCompleteListener) activity;
} catch (final ClassCastException e) {
  throw new ClassCastException(activity.toString() + " must implement OnCompleteListener"); }
}
public void sendResult(int result, User user) { 
  this.mListener.onComplete(result, user);
}
//This should be done in your activity
public class MainActivity extends AppCompatActivity implements AddUserDialogFragment.OnCompleteListener{
  @Override
  public void onComplete(int result, User user) {
   if (result == Activity.RESULT_OK) { userArrayList.add(user); setUpRecyclerView();}
  } 
}


Q#5 Can you make an entire app without ever using Fragments? Why or why not? Are there any special cases when you absolutely have to use or should use Fragments?

Yes, we can make entire app without using fragments as we know activity is a main component of app and 
activity can exist without fragments. but to use fragment we must have at least one activity in our app
which can hold a fragment.
yes we can use fragments when we need to show multiple or two different views on same/single screen
likewise if we want to have a layout like detail flow screen we must have to use fragments, 
one is for Listview and other one is for detail view which is a great way to deal with big screen sizes e.g tablets.


Q#6 What makes an AsyncTask such an annoyance to Android developers? Detail some of the issues with AsyncTask, and how to potentially solve them.

On orientation change, Activity is destroyed and recreated. after recreation AsyncTask reference to the destroyed
Activity is invalid, so onPostExecute() will have no effect on the new Activity.solution to this problem is we have to
hold a reference to last AsyncTask before configuration changes,
we can hold a global reference or we can pass it Activity.onRetainNonConfigurationInstance().
If AsyncTask is running and you close the entire application AsyncTask will keep on running until unless you cancel it.
so this can effect your app by unnecessary background tasks, or leaking of memory. so we must have to cancel it
by calling AsyncTask.cancel
AsyncTask is the easiest way to get a background thread, but it is ill-suited for repetitive and long running work.
Instead of using an AsyncTask, we are going to create a dedicated background thread. 
This is the most common way to implement downloading on an as-needed basis.
AsyncTask is intended for work that is short lived and not repeated too often.
A more compelling technical reason is that in Android 3.2 AsyncTask changed its implementation in a significant way. 
Starting with Android 3.2, AsyncTask does not create a thread for each instance of AsyncTask. Instead, 
it uses something called an Executor to run background work for all AsyncTasks on a single background thread. 
That means that each AsyncTask will run one after the other. A long running AsyncTask will hog the thread,
preventing other AsyncTasks from getting any CPU time.
