//---------------------------------------------------------------------------
#include <vcl\vcl.h>
#pragma hdrstop

#include "Unit1.h"
#include <string.h>
//---------------------------------------------------------------------------
#pragma resource "*.dfm"
TForm1 *Form1;
int main_gen(int argc, char **argv);

//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
	: TForm(Owner)
{
}
//---------------------------------------------------------------------------
void __fastcall TForm1::Button1Click(TObject *Sender)
{
   char *commline[15] = {"dbgen.exe", "lit", "-ntrans", "       ",
   						 "-tlen", "       ", "-nitems", "       ",
                         "-npats", "       ", "-patlen", "       ",
                         "-fname", "             ", "-ascii"};
   strcpy(commline[3], Form1->Ntrans->Text.c_str());
   strcpy(commline[5], Form1->Tlen->Text.c_str());
   strcpy(commline[7], Form1->Nitems->Text.c_str());
   strcpy(commline[9], Form1->Npats->Text.c_str());
   strcpy(commline[11], Form1->Patlen->Text.c_str());
   strcpy(commline[13], Form1->Fname->Text.c_str());
   Form1->Button1->Enabled = False;
   main_gen(15, commline);
   Form1->Button1->Enabled = True;
}

