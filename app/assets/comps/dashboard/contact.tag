<contact>
  <div class="container">

    <!-- Page Heading/Breadcrumbs -->
    <h1 class="mt-4 mb-3">Contact
      <small>Subheading</small>
    </h1>

    <ol class="breadcrumb">
      <li class="breadcrumb-item">
        <a href="index.html">Home</a>
      </li>
      <li class="breadcrumb-item active">Contact</li>
    </ol>

    <!-- Content Row -->
    <div class="row">
      <!-- Map Column -->
      <div class="col-lg-8 mb-4">
        <!-- Embedded Google Map -->
        <iframe width="100%" height="400px" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1009.4320155915113!2d123.90211682920037!3d10.31905189953956!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x33a9993ecd0932cd%3A0x3e1fa66d978712e2!2sProvest!5e1!3m2!1sen!2sph!4v1537869198006" allowfullscreen></iframe>
      </div>
      <!-- Contact Details Column -->
      <div class="col-lg-4 mb-4">
        <h3>Contact Details</h3>
        <p>
          Provest Real Estate Agency
          <br>Apitong St, Cebu City, 6000 Cebu
          <br>
        </p>
        <p>
          <abbr title="Phone">Phone</abbr>: (032) 260 1899
        </p>
        <p>
          <abbr title="Email">Email</abbr>:
          <a href="mailto:provest@email.com" style="color: #000;"> provest@email.com
          </a>
        </p>
        <p>
          <abbr title="Hours">Hours Open</abbr>: Monday - Saturday: 9:00 AM to 8:00 PM
        </p>
      </div>
    </div>
    <!-- /.row -->

    <!-- Contact Form -->
    <!-- In order to set the email address and subject line for the contact form go to the bin/contact_me.php file. -->
    <div class="row">
      <div class="col-lg-8 mb-4">
        <h3>Send us a Message</h3>
        <form name="sentMessage" id="contactForm" novalidate>
          <div class="control-group form-group">
            <div class="controls">
              <label>Full Name:</label>
              <input type="text" class="form-control" id="name" required data-validation-required-message="Please enter your name.">
              <p class="help-block"></p>
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label>Phone Number:</label>
              <input type="tel" class="form-control" id="phone" required data-validation-required-message="Please enter your phone number.">
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label>Email Address:</label>
              <input type="email" class="form-control" id="email" required data-validation-required-message="Please enter your email address.">
            </div>
          </div>
          <div class="control-group form-group">
            <div class="controls">
              <label>Message:</label>
              <textarea rows="10" cols="100" class="form-control" id="message" required data-validation-required-message="Please enter your message" maxlength="999" style="resize:none"></textarea>
            </div>
          </div>
          <div id="success"></div>
          <!-- For success/fail messages -->
          <button type="submit" class="btn btn-primary" id="sendMessageButton">Send Message</button>
        </form>
      </div>

    </div>
    <!-- /.row -->

  </div>
  <script>
  var self = this
    helper = window.Helpers.factory()

    this.on('mount', function() {
      helper.setTitle({ title: 'Contact Us' })
    })
  </script>
</contact>
