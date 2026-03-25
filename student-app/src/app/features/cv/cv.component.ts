import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormArray,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CvService } from './cv.service';
import { AuthService } from '../../core/auth/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MAT_DATE_LOCALE } from '@angular/material/core';

@Component({
  selector: 'app-cv',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
  ],
  templateUrl: './cv.component.html',
  styleUrls: ['./cv.component.css'],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' }],
})

export class CvComponent implements OnInit {
  hasCv = false;
  imagePreview: string | null = null;

  form = this.fb.group({
    photoUrl: [''],
    summary: ['', Validators.required],
    educations: this.fb.array<FormGroup>([]),
    experiences: this.fb.array<FormGroup>([]),
    skills: this.fb.array<FormGroup>([]),
    languages: this.fb.array<FormGroup>([]),
    interests: this.fb.array<FormGroup>([]),
  });

  constructor(
    private fb: FormBuilder,
    private cvService: CvService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    const user = this.authService.getUser();
    if (!user) {
      this.router.navigate(['/login']);
      return;
    }

    this.loadCv();
  }

  loadCv() {
    this.cvService.getCv().subscribe({
      next: (cv) => {
        this.imagePreview = cv?.photoUrl || null;
        this.clearArrays();
        this.hasCv = !!cv;

        if (!cv) {
          this.addEducation();
          this.addExperience();
          this.addSkill();
          return;
        }

        this.form.patchValue({
          photoUrl: cv.photoUrl,
          summary: cv.summary,
        });

        cv.educations.forEach((e) =>
          this.educations.push(
            this.fb.group({
              id: [e.id],
              institution: [e.institution, Validators.required],
              degree: [e.degree, Validators.required],
              fieldOfStudy: [e.fieldOfStudy, Validators.required],
              startYear: [e.startYear, Validators.required],
              endYear: [e.endYear, Validators.required],
            }),
          ),
        );

        cv.experiences.forEach((e) =>
          this.experiences.push(
            this.fb.group({
              id: [e.id],
              companyName: [e.companyName, Validators.required],
              position: [e.position, Validators.required],
              description: [e.description, Validators.required],
              startDate: [this.parseDate(e.startDate), Validators.required],
              endDate: [this.parseDate(e.endDate), Validators.required],
            }),
          ),
        );

        cv.skills.forEach((s) =>
          this.skills.push(
            this.fb.group({
              id: [s.id],
              skillName: [s.skillName, Validators.required],
              skillLevel: [s.skillLevel, Validators.required],
            }),
          ),
        );

        cv.languages.forEach((l) =>
          this.languages.push(
            this.fb.group({
              id: [l.id],
              languageName: [l.languageName, Validators.required],
              level: [l.level, Validators.required],
            }),
          ),
        );

        cv.interests.forEach((i) =>
          this.interests.push(
            this.fb.group({
              id: [i.id],
              interestName: [i.interestName, Validators.required],
            }),
          ),
        );
      },

      error: () => {
        this.hasCv = false;
        this.addEducation();
        this.addExperience();
        this.addSkill();
      },
    });
  }

  save() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      alert('Please fill all required fields');
      return;
    }

    const formValue = this.form.getRawValue();

    const body = {
      photoUrl: formValue.photoUrl,
      summary: formValue.summary,

      educations: formValue.educations,
      experiences: formValue.experiences.map((exp: any) => ({
        ...exp,
        startDate: this.formatDate(exp.startDate),
        endDate: this.formatDate(exp.endDate),
      })),
      skills: formValue.skills,
      languages: formValue.languages,
      interests: formValue.interests,
    };

    const request = this.hasCv
      ? this.cvService.updateCv(body)
      : this.cvService.createCv(body);

    request.subscribe({
      next: () => {
        alert(this.hasCv ? 'Updated!' : 'Created!');
        this.loadCv();
      },
      error: (err) => {
        console.error(err);
        alert('Error saving CV');
      },
    });
  }

  get educations(): FormArray<FormGroup> {
    return this.form.get('educations') as FormArray<FormGroup>;
  }

  get experiences(): FormArray<FormGroup> {
    return this.form.get('experiences') as FormArray<FormGroup>;
  }

  get skills(): FormArray<FormGroup> {
    return this.form.get('skills') as FormArray<FormGroup>;
  }

  get languages(): FormArray<FormGroup> {
    return this.form.get('languages') as FormArray<FormGroup>;
  }

  get interests(): FormArray<FormGroup> {
    return this.form.get('interests') as FormArray<FormGroup>;
  }

  clearArrays() {
    this.educations.clear();
    this.experiences.clear();
    this.skills.clear();
    this.languages.clear();
    this.interests.clear();
  }

  addEducation() {
    this.educations.push(
      this.fb.group({
        id: [null],
        institution: ['', Validators.required],
        degree: ['', Validators.required],
        fieldOfStudy: ['', Validators.required],
        startYear: ['', Validators.required],
        endYear: ['', Validators.required],
      }),
    );
  }

  addExperience() {
    this.experiences.push(
      this.fb.group({
        id: [null],
        companyName: ['', Validators.required],
        position: ['', Validators.required],
        description: ['', Validators.required],
        startDate: ['', Validators.required],
        endDate: ['', Validators.required],
      }),
    );
  }

  addSkill() {
    this.skills.push(
      this.fb.group({
        id: [null],
        skillName: ['', Validators.required],
        skillLevel: ['', Validators.required],
      }),
    );
  }

  addLanguage() {
    this.languages.push(
      this.fb.group({
        id: [null],
        languageName: ['', Validators.required],
        level: ['', Validators.required],
      }),
    );
  }

  addInterest() {
    this.interests.push(
      this.fb.group({ id: [null], interestName: ['', Validators.required] }),
    );
  }

  removeEducation(i: number) {
    this.educations.removeAt(i);
  }

  removeExperience(i: number) {
    this.experiences.removeAt(i);
  }

  removeSkill(i: number) {
    this.skills.removeAt(i);
  }

  removeLanguage(i: number) {
    this.languages.removeAt(i);
  }

  removeInterest(i: number) {
    this.interests.removeAt(i);
  }

  removePhoto() {
    this.imagePreview = null;
    this.form.patchValue({ photoUrl: null });
  }

  deleteCv() {
    this.cvService.deleteCv().subscribe({
      next: () => {
        alert('Deleted!');
        this.hasCv = false;
        this.form.reset();
        this.clearArrays();
        this.imagePreview = null;
        this.addEducation();
        this.addExperience();
        this.addSkill();
      },
      error: () => alert('Error deleting CV'),
    });
  }

  downloadPdf() {
    if (!this.hasCv) return;
    this.cvService.downloadPdf().subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      window.open(url);
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => (this.imagePreview = reader.result as string);
    reader.readAsDataURL(file);
    const formData = new FormData();
    formData.append('file', file);
    this.cvService.uploadImage(formData).subscribe({
      next: (url) => this.form.patchValue({ photoUrl: url }),
      error: () => alert('Upload failed'),
    });
  }

  //HELPERS
  private parseDate(dateStr: any): Date | null {
    if (!dateStr) return null;
    if (dateStr instanceof Date) return dateStr;

    if (typeof dateStr === 'string') {
      const parts = dateStr.split('-');
      if (parts.length === 3) {
        const day = parseInt(parts[0], 10);
        const month = parseInt(parts[1], 10) - 1;
        const year = parseInt(parts[2], 10);

        const date = new Date(year, month, day);
        return isNaN(date.getTime()) ? null : date;
      }
    }

    const fallbackDate = new Date(dateStr);
    return isNaN(fallbackDate.getTime()) ? null : fallbackDate;
  }

  private formatDate(date: any): string | null {
    if (!date) return null;
    const d = new Date(date);
    if (isNaN(d.getTime())) return date;

    const day = String(d.getDate()).padStart(2, '0');
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const year = d.getFullYear();
    return `${day}-${month}-${year}`;
  }
}
