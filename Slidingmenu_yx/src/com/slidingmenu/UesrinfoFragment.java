package com.slidingmenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import com.slidingmenu.Util.BitmapUtil;
import com.slidingmenu.view.ButtonLayout;
import com.slidingmenu.view.SocialButtonLayout;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class UesrinfoFragment extends Fragment{

	private TextView buaamuse;
	private ButtonLayout BindButton;
	private SocialButtonLayout BoyaButton;
	private SocialButtonLayout WorkButton;
	private ImageView head;
	
	private Button btn_bind;
	private Button btn_edit;
	
	private static final int CAMERA_WITH_DATA = 3021;
	private static final int PHOTO_PICKED_WITH_DATA = 3025;
	private static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/Pa/");

	private File mCurrentPhotoFile;
	private final Context mcontext = this.getActivity();
	private static Uri imageUri;
	
	private int RESULT_OK = -1;
	private Boolean myavaternull = true;
	private File myimagefile = null;
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
	private SharedPreferences Mysp;
	private Editor Myeditor;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_userinfo_frame, null);
        buaamuse = (TextView)view.findViewById(R.id.userinfo);
        buaamuse.setText("个人信息");
        
        Mysp = ((MainActivity)getActivity()).mysp;
        Myeditor = Mysp.edit();
        
        head = (ImageView)view.findViewById(R.id.head_avatar);
        
        String imageBase64 = Mysp.getString("avatar", "null");
		if (imageBase64.equalsIgnoreCase("null")) {
			Resources res = getResources();
			Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.test);
			Bitmap mypic = BitmapUtil.drawRoundBitmap(bitmap, 5);

			head.setImageBitmap(mypic);
		} else {
			byte[] base64Bytes = Base64.decode(imageBase64, Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
			head.setImageDrawable(Drawable.createFromStream(bais, "image"));
		}
        
        imageUri = Uri.parse(IMAGE_FILE_LOCATION);
        
        head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doPickPhotoAction();
			}
		});
        
        BindButton = (ButtonLayout)view.findViewById(R.id.nick_bind);
        BoyaButton = (SocialButtonLayout)view.findViewById(R.id.boya_button);
        WorkButton = (SocialButtonLayout)view.findViewById(R.id.work_button);
        
        btn_bind = (Button)view.findViewById(R.id.btn_bind);
        btn_edit = (Button)view.findViewById(R.id.btn_edit);
        
        BoyaButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent awardIntent = new Intent((MainActivity)getActivity(),AwardDetail.class);
				startActivity(awardIntent);
			}
		});
        
        WorkButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent workIntent = new Intent((MainActivity)getActivity(),WorkDetail.class);
				startActivity(workIntent);
			}
		});
        
        btn_bind.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loginIntent = new Intent((MainActivity)getActivity(), UserLogin.class);
				startActivity(loginIntent);
			}
		});
        
        btn_edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent editIntent = new Intent((MainActivity)getActivity(),UserEdit.class);
				startActivity(editIntent);
			}
		});
        
        BindButton.setTitleText("身份绑定状态");
        BoyaButton.setTitleText("我的奖学金");
        WorkButton.setTitleText("我的勤工俭学");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

    }
    
    public void doPickPhotoAction(){
    	Context context = this.getActivity();

		// Wrap our context to inflate list items using correct theme
		final Context dialogContext = new ContextThemeWrapper(context,
				android.R.style.Theme_Light);
		String cancel = getString(R.string.cancel).toString();
		String[] choices;
		choices = new String[2];
		choices[0] = getString(R.string.take_photo);
		choices[1] = getString(R.string.pick_photo);
		final ListAdapter adapter = new ArrayAdapter<String>(dialogContext,
				android.R.layout.simple_list_item_1, choices);

		final AlertDialog.Builder builder = new AlertDialog.Builder(
				dialogContext);
		builder.setTitle(R.string.pickapicture);
		builder.setSingleChoiceItems(adapter, -1,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switch (which) {
						case 0: {
							String mystatus = Environment
									.getExternalStorageState();
							if (mystatus.equals(Environment.MEDIA_MOUNTED)) {
								doTakePhoto();
							} else {
								// Toast.makeText(this, "signin Successfully!",
								// Toast.LENGTH_LONG).show();
							}
							break;

						}
						case 1:
							doPickPhotoFromGallery();
							break;
						}
					}
				});
		builder.setNegativeButton(cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}

				});
		builder.create().show();
    }
    
    protected void doTakePhoto() {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName() + ".jpg");
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			// System.out.println("do take photo "+ mCurrentPhotoFile);
			// Intent cameraIntent = new
			// Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
//			Toast.makeText(this, R.string.photoPickerNotFoundText,
//					Toast.LENGTH_SHORT).show();
		}
	}
    
    public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'Pa'_yyyy-MM-dd-HH-mm-ss");
		return dateFormat.format(date);
	}

	protected void doPickPhotoFromGallery() {
		try {
			// Launch picker to choose photo for selected contact
			Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// pick a pic from gallary
		public static Intent getPhotoPickIntent() {
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 100);
			intent.putExtra("outputY", 100);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true); // no face detection
			// startActivityForResult(intent, );
			return intent;
		}
		
		private Bitmap decodeUriAsBitmap(Uri uri) {
			Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver()
						.openInputStream(uri));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			return bitmap;
		}
    
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			System.out.println("onActivityResult" + requestCode + "resultcode"
					+ resultCode + "intent" + data);

			if (resultCode != RESULT_OK)
				return;
			switch (requestCode) {
			case PHOTO_PICKED_WITH_DATA: {
				if (imageUri != null) {

					System.out.println("imageUri:"+imageUri);
					Bitmap photo = decodeUriAsBitmap(imageUri);// decode bitmap
					Bitmap mypic = BitmapUtil.drawRoundBitmap(photo, 7);
					head.setImageBitmap(mypic);
					myavaternull = false;
					try {
						myimagefile = saveMyBitmap(getPhotoFileName(), photo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					mypic.compress(CompressFormat.JPEG, 50, baos);
					String imageBase64 = new String(Base64.encodeToString(
							baos.toByteArray(), Base64.DEFAULT));
					Myeditor.putString("avatar", imageBase64);
					Myeditor.commit();
					((MainActivity)getActivity()).status = 1;
					photo.recycle();
					
					break;
				} else {
					break;
				}
			}
			case CAMERA_WITH_DATA: {
				System.out.println("carera with data");
				rotatePic(mCurrentPhotoFile.toString());
				doCropPhoto(mCurrentPhotoFile);
				((MainActivity)getActivity()).status = 1;
				
				break;
			}
			}
		}

		public void rotatePic(String path) {
			Bitmap cameraBitmap, bitmap = null;
			BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
			bitmapOptions.inSampleSize = 2;
			File file = new File(path);

			int degree = readPictureDegree(file.getAbsolutePath());
			cameraBitmap = BitmapFactory.decodeFile(path, bitmapOptions);

			bitmap = rotaingImageView(degree, cameraBitmap);
			
			try {
				mCurrentPhotoFile = saveMyBitmap("temp", bitmap);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {

			Matrix matrix = new Matrix();
			
			matrix.postRotate(angle);
			System.out.println("angle2=" + angle);

			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
					bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			return resizedBitmap;
		}

		public static int readPictureDegree(String path) {
			int degree = 0;
			try {
				ExifInterface exifInterface = new ExifInterface(path);
				int orientation = exifInterface.getAttributeInt(
						ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_NORMAL);
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return degree;
		}

		protected void doCropPhoto(File f) {
			try {
				Bitmap photo = decodeUriAsBitmap(Uri.fromFile(f));
				
				final Intent intent = getCropImageIntent(Uri.fromFile(f));
				photo.recycle();
				startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
			} catch (Exception e) {
//				Toast.makeText(this, R.string.photoPickerNotFoundText,
//						Toast.LENGTH_SHORT).show();
			}
		}

		public static Intent getCropImageIntent(Uri photoUri) {
			
			
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(photoUri, "image/*");
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 100);
			intent.putExtra("outputY", 100);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", false);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			intent.putExtra("noFaceDetection", true);
			return intent;
		}

		public File saveMyBitmap(String bitName, Bitmap mBitmap) throws IOException {
			PHOTO_DIR.mkdirs();
			File f = new File(Environment.getExternalStorageDirectory() + "/Pa/"
					+ bitName + ".jpg");
			System.out.println("the path save the img" + f.toString());
			f.createNewFile();
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			try {
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return f;
		}

}
